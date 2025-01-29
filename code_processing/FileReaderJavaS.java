package code_processing;

import code_processing.exceptions.*;
import databases.VariableDataBase;
import variables.exceptions.InvalidFinalVariableInitializationException;
import variables.exceptions.InvalidFormatException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class handles the iteration over the file
 */
public class FileReaderJavaS {
    private RowProcessing rowProcessing;
    private VariableDataBase variableDataBase;
    public FileReaderJavaS(VariableDataBase variableDataBase) {
        this.variableDataBase = variableDataBase;
        this.rowProcessing = new RowProcessing(this.variableDataBase);
    }

    /**
     * this function checks basic error (syntax) in the file
     * @param inputFile the file name we got
     * @return 0 if file is correct ' 1 syntax error, 2 io exception
     */
    public int readAndCheckBasicErrorFile(String inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))){
            String line;
            int lineNumber = 0;
            int layer = 0;
            int bracketCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                // if line starts with single line comment or blank continue
                // to the next line in the file
                if(line.isBlank()||line.startsWith("//")){
                    lineNumber++;
                    continue;
                }
                invalidEndingRow(line,lineNumber);
                invalidComments(line,lineNumber);
                invalidArray(line,lineNumber);
                checkValidityInitializationVariable(line);
                if(line.strip().endsWith("{")){
                    bracketCount++;
                    layer++;
                }
                if(line.strip().endsWith("}")){
                    if(bracketCount == 0){
                        throw new InvalidAmountOfClosingBracketsException(lineNumber);
                    }
                    bracketCount--;
                    layer--;
                }
                rowProcessing.isSettingRow(line); // if not any of the above must be set line
                lineNumber++;
            }
        }
        catch (IOException e) {
               return 2;
        }
        catch (InvalidMultiLineCommentException|JavaDocException
                |InvalidSingleCommentLineException|InvalidEndingForSentenceException e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }


    //---------------------basic check format is correct---------------------------
    /**
     * this function checks is there invalid comment if there are throw exception
     * @param line the line we check is there exception
     * @param lineNumber the line number in the file
     */
    public void invalidComments(String line, int lineNumber){
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

    /**
     * this function checks is the line ends with ';' , '{' or '}' if not throw exception
     * @param line the line we check how it ends
     * @param lineNumber the line number in the file
     */
    public void invalidEndingRow(String line, int lineNumber){
        String stripedLine = line.strip();
        if(stripedLine.endsWith(";")||stripedLine.endsWith("{") ||stripedLine.endsWith("}")){
            return;
        }
        throw new InvalidEndingForSentenceException(lineNumber);
    }

    /**
     * this function checks is there array if there is throw exception
     * @param line the exact line we check
     * @param lineNumber the line number in the file
     */
    public void invalidArray(String line, int lineNumber){
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

    /**
     * this function checks validity of format of initialization of variable
     * @param line the line we initialize variable
     */
    public void checkValidityInitializationVariable(String line){
        boolean flag = true;
        if(line.strip().startsWith("int")
                ||line.strip().startsWith("double")
                ||line.strip().startsWith("boolean")
                ||line.strip().startsWith("char")
                ||line.strip().startsWith("String")){
            flag = this.rowProcessing.isMixed(line);
        }
        else if(line.strip().startsWith("final")) {
            flag = this.rowProcessing.isCorrectFormatFinal(line);
        }
        if(!flag){
            throw new InvalidFormatException();
        }
    }

    /**
     * this function checks validity of format of if and while
     * @param line line we do if or while
     */
//    public void checkValidityIfWhile(String line, int layer){
//        if(line.strip().startsWith("if")){
//            if(layer == 0){
//                throw new InvalidFormatIfAndWhile();
//            }
//        }
//        else if(line.strip().startsWith("while")) {
//            if(layer == 0){
//                throw new InvalidFormatIfAndWhile();
//            }
//        }
//    }



    // check set is valid (set of variables)
    // check is syntax of method is valid
}
