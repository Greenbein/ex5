package code_processing;

import code_processing.condition_exceptions.InvalidFormatForIfCommandException;
import code_processing.condition_exceptions.InvalidFormatForWhileCommandException;
import code_processing.condition_exceptions.InvalidVarTypeForConditionException;
import code_processing.exceptions.*;
import databases.MethodsDataBase;
import databases.VariableDataBase;
import methods.Method;
import methods.exceptions.DoubleFunctionDeclaration;
import methods.exceptions.IncorrectFunctionCallingFormat;
import variables.exceptions.DoubleCreatingException;
import variables.exceptions.InvalidFinalVariableInitializationException;
import variables.exceptions.InvalidFormatException;
import variables.exceptions.UnreachableVariableException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class handles the iteration over the file
 */
public class FileReaderJavaS {
    // -------------------------constants-----------------------------
    private static final String LINE_STARS_WITH_VOID = "^\\s*void\\s.*$";
    private static final String LINE_STARS_WITH_IF = "^\\s*if.*$";
    private static final String LINE_STARS_WITH_WHILE = "^\\s*while.*$";
    private static final String LINE_IS_RETURN= "^\\s*return;\\s*$";
    private static final String START_SINGLE_COMMENT = "//";
    private static final String STRING = "String ";
    private static final String INTEGER = "int ";
    private static final String DOUBLE = "double ";
    private static final String BOOLEAN = "boolean ";
    private static final String CHAR = "char ";
    private static final String FINAL = "final ";
    private static final String CLOSING_PARENTHESIS = "}";
    //--------------------privates--------------------
    private RowProcessing rowProcessing;
    private VariableDataBase variableDataBase;
    private MethodProcessing methodProcessing;
    private ConditionProcessing conditionProcessing;
    private MethodsDataBase methodsDataBase;
    private int layer;
    private int lineNumber;
    private int numberOfReturns;


    /**
     * default constructor FileReaderJavaS
     * @param variableDataBase given variable database
     */
    public FileReaderJavaS(VariableDataBase variableDataBase) {
        this.variableDataBase = variableDataBase;
        this.rowProcessing = new RowProcessing(this.variableDataBase);
        this.methodProcessing = new MethodProcessing();
        this.conditionProcessing = new ConditionProcessing(variableDataBase);
        this.methodsDataBase = new MethodsDataBase();
        this.layer = 0;
        this.lineNumber = 0;
        this.numberOfReturns = 0; // following after number of returns should be in the end 0
    }

    /**
     * this function checks basic error (syntax) in the file
     * @param inputFile the file name we got
     * @return 0 if file is correct ' 1 syntax error, 2 io exception
     */
    public int readAndCheckBasicErrorFile(String inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(isSskippableLine(line)) { continue; }
                isInvalidLine(line);
                if(validateVariableAndInitializeGlobalVariable(line)){continue;}
                if (validateAndAddMethodToDB(line)) {continue;}
                if(processConditionalStatement(line)){continue;}
                if(processClosingParenthesis(line)){continue;}
                if(checkAndUpdateReturnTracker(line)){continue;}
                if(isValidFunctionCall(line)){continue;}
                if(isValidSettingRow(line)){continue;}
                throw new InvalidFormatLine(this.lineNumber);
            }
        }
        catch (IOException e) {
              System.err.println("Invalid file name: file does not exist");
              return 2;
        }
        catch (InvalidEndingForSentenceException | InvalidMultiLineCommentException |
               JavaDocException | InvalidSingleCommentLineException | InvalidArrayException |
               InvalidFormatException | InvalidFinalVariableInitializationException |
               DoubleCreatingException | invalidVariableTypeException |
               DoubleFunctionDeclaration | InvalidFormatFunctionException |
               InvalidFormatForWhileCommandException | InvalidFormatForIfCommandException |
               InvalidVarTypeForConditionException | UnreachableVariableException |
               InvalidAmountOfClosingBracketsException |NoReturnInFunctionException|
               InvalidReturnException | IncorrectFunctionCallingFormat | InvalidFormatLine e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    public int functionsCheckInFile(String inputFile){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            this.lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if(!isLineIsMethod(line) && this.layer == 0){continue;}
                if(isLineIsMethod(line)){
                    this.methodProcessing.loadFunctionParametersToDB(line,variableDataBase);
                    this.layer++;
                    this.lineNumber++;
                    continue;
                }
                if(line.strip().endsWith("{")){
                    this.layer++;
                }
                if(line.strip().equals("}")){
                    this.variableDataBase.removeLayer(this.layer);
                    this.layer--;
                    this.lineNumber++;
                    continue;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Invalid file name: file does not exist");
            return 2;
        }
        return 0;
    }

    //---------------------basic check format is correct---------------------------
    // this function checks is it a skippable  line
    private boolean isSskippableLine(String line) {
        if(line.isBlank()||line.startsWith(START_SINGLE_COMMENT)){
            this.lineNumber++;
            return true;
        }
        return false;
    }

    // this function checks is line invalid (invalid format of line)
    private void isInvalidLine(String line) {
        invalidEndingRow(line,this.lineNumber); // checks if line ends with {,},;
        invalidComments(line,this.lineNumber); // check is there invalid comments in line
        // checks are we trying to initialize arr (invalid)
        invalidArray(line,this.lineNumber);
    }

    // this function checks is there invalid comment if there are throw exception
    private void invalidComments(String line, int lineNumber){
        if(line.contains("/*")){
            throw new InvalidMultiLineCommentException(lineNumber);
        }
        if(line.contains("/**")){
            throw new JavaDocException(lineNumber);
        }
        if(line.contains("//")){
            throw new InvalidSingleCommentLineException(lineNumber);
        }
    }

    //this function checks is the line ends with ';' , '{' or '}' if not throw exception
    private void invalidEndingRow(String line, int lineNumber){
        String stripedLine = line.strip();
        if(stripedLine.endsWith(";")||stripedLine.endsWith("{") ||stripedLine.endsWith("}")){
            return;
        }
        throw new InvalidEndingForSentenceException(lineNumber);
    }

    //this function checks is there array if there is throw exception
    private void invalidArray(String line, int lineNumber){
        String finalArray = "\\s*final\\s+(int|boolean|char|String|double)\\s*\\[].*";
        Pattern finalPattern = Pattern.compile(finalArray);
        Matcher finalMatcher = finalPattern.matcher(line);
        String notFinalArray = "\\s*(int|boolean|char|String|double)\\s*\\[].*";
        Pattern notFinalPattern = Pattern.compile(notFinalArray);
        Matcher notFinalMatcher = notFinalPattern.matcher(line);
        if(finalMatcher.matches()||notFinalMatcher.matches()){
            throw new InvalidArrayException(lineNumber);
        }


        //throw new InvalidArrayException(lineNumber); -----aaaaaaaaa excception---
    }

    // this function checks validity of format of initialization of variable
    // if the line start with primitive type(int/double/boolean/string/char)
    // check its correctness and process it in the case it's correct statement
    // and we in the global layer add to the database.
    // if the process ended successfully return true. else there
    // is a mistake and we throw an exception.
    // similarly do the same process if it is the line starts with final with specific
    // exceptions for the case of final.
    // if the line don't start with one of these keywords return false.
    private boolean validateVariableAndInitializeGlobalVariable(String line){
        if(line.strip().startsWith(INTEGER)
                ||line.strip().startsWith(DOUBLE)
                ||line.strip().startsWith(BOOLEAN)
                ||line.strip().startsWith(CHAR)
                ||line.strip().startsWith(STRING)){
            this.rowProcessing.isMixed(line);
            if(this.layer == 0){
                rowProcessing.extractDataMixed(line,this.layer,this.lineNumber);
            }
            this.lineNumber++;
            return true;
        }
        else if(line.strip().startsWith(FINAL)) {
            this.rowProcessing.isCorrectFormatFinal(line);
            if(this.layer == 0){
                rowProcessing.extractDataFinal(line,this.layer,this.lineNumber);
            }
            this.lineNumber++;
            return true;
        }
        return false;
    }

    // the function validates is the line start with void
    // if not return false because it's not method declaration
    // if it is first check is the format of the declaration is valid
    // if it is then add it to DB update layer and line number, layer and amount
    // of returns we should get and return true
    // if the format is incorrect or such method exists throw exception
    private boolean validateAndAddMethodToDB(String line){
        Pattern patternVoid = Pattern.compile(LINE_STARS_WITH_VOID);
        Matcher mVoid = patternVoid.matcher(line);
        if(mVoid.matches()){
            this.methodProcessing.isCorrectFormatFunction(line.strip());
            this.methodProcessing.processFunctionDeclaration
                    (line,this.methodsDataBase);
            this.layer++;
            this.lineNumber++;
            this.numberOfReturns++;
            return true;
        }
        return false;
    }

    // this function checks is the line conditional statement
    // if the line is a valid conditional statement update the
    // layer and line number accordingly
    // if the line is invalid condition throw exception.
    // if the line is not a condition return false
    private boolean  processConditionalStatement(String line){
        Pattern patternWhile = Pattern.compile(LINE_STARS_WITH_WHILE);
        Matcher mWhile = patternWhile.matcher(line);
        if(mWhile.matches()){
           conditionProcessing.isCorrectWhileFormat(line,this.layer);
           this.layer++;
           this.lineNumber++;
           return true;
        }
        Pattern patternIf = Pattern.compile(LINE_STARS_WITH_IF);
        Matcher mWIf = patternIf.matcher(line);
        if(mWIf.matches()){
            conditionProcessing.isCorrectIfFormat(line,this.layer);
            this.layer++;
            this.lineNumber++;
            return true;
        }
        return false;
    }

    // this function handles the case we see return;
    // check in the end is number of returns 0 if not
    // there is a function without a return
    private boolean checkAndUpdateReturnTracker(String line){
        Pattern patternReturn = Pattern.compile(LINE_IS_RETURN);
        Matcher mReturn = patternReturn.matcher(line);
        if(mReturn.matches()){
            if(this.layer == 0){
                throw new InvalidReturnException(this.lineNumber);
            }
            if(this.layer == 1 && this.numberOfReturns > 0){
                this.numberOfReturns--;
            }
            this.lineNumber++;
            return true;
        }
        return false;
    }

    // check is the number of returns after going ovver all the file
    // is 0 if it is return true else re

    // this function handles the case the line is a call to another
    // function if the format is correct return true else throw
    // an exception
    private boolean isValidFunctionCall(String line){
        if(this.methodProcessing.isMethodUsage(line)){
            this.methodProcessing.isCorrectMethodUsageFormat(line);
            this.lineNumber++;
        }
        return false;
    }

    // this function check is the row is a setting row
    // if it is a setting row and the layer is 0 set the global variables and return true
    // if it isn't setting row return false
    private boolean isValidSettingRow(String line){
        if(this.rowProcessing.isSettingRow(line)){
            //set db
            this.lineNumber++;
        }
        return false;
    }

    // this function process a line that contains only "}" and update
    // the layer and line number according to it
    // if the line is valid decrement the layer and increase the line number
    // if after decrement the current layer is 0 and number of return is
    // bigger than zero than we didn't return in this function, and we would
    // throw an exception
    // if the line contains only "}" but the layer is zero
    // throw an exception because there is invalid amount of closing brackets.
    // if the line don't equal to "}" return false
    private boolean processClosingParenthesis(String line){
        if(line.strip().equals(CLOSING_PARENTHESIS)){
            if(this.layer == 0){
                throw new InvalidAmountOfClosingBracketsException(this.lineNumber);
            }
            this.layer--;
            if(this.layer == 0 && this.numberOfReturns  > 0){
                throw new NoReturnInFunctionException(this.lineNumber);
            }
            this.lineNumber++;
            return true;
        }
        return false;
    }

    // -----------------functions for second reading (reading of functions)--------

    // this function returns is line is a method or not
    private boolean isLineIsMethod(String line){
        Pattern patternVoid = Pattern.compile(LINE_STARS_WITH_VOID);
        Matcher mVoid = patternVoid.matcher(line);
        return mVoid.matches();
    }
}
