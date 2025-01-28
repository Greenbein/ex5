package row_processing;

import row_processing.exceptions.*;
import valid_name.name_exceptions.NameAfterVariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowValidations {
    private void checkSlash(String text, int lineNumber) {
        // empty line return
        if(text.isBlank()){
            return;
        }
        // if line starts with single line comment return
        if(text.startsWith("//")){
            return;
        }
        //--------- exceptions for invalid comments ----------
        // multi-line comment invalid
        if(text.contains("/*")){
            throw new InvalidMultiLineCommentException(lineNumber);
        }
        // javadoc comment is invalid
        if(text.contains("/**")){
            throw new JavaDocException(lineNumber);
        }
        // single line comments not in the start of the sentence invalid
        if(text.contains("//")){
            throw new InvalidSingleCommentLineException(lineNumber);
        }
        // sentence must end with ';' , '{' or '}'
        if(!(text.strip().endsWith(";")||text.strip().endsWith("{")
                ||text.strip().endsWith("}"))){
            throw new InvalidEndingForSentenceException(lineNumber);
        }
        String [] textWords = text.split("\\s+");
        int initializersOccasionsInARow = 0;
        for (String word : textWords) {
            // operators are invalid
            if(word.equals("+")||word.equals("-")||word.equals("*")||word.equals("/")){
                throw new InvalidUseOperatorsExceptions(lineNumber);
            }
            // array is invalid
            if(word.equals("[]")){
                throw new InvalidArrayException(lineNumber);
            }
            // we can transfer for variable name
            // not valid more than one initializer in one row
            if(word.equals("int")||word.equals("double")||word.equals("char")
                    ||word.equals("String")||word.equals("boolean")){
                initializersOccasionsInARow ++;
            }
        }
        // not valid more than one initializer in one row
        if(initializersOccasionsInARow > 1){
            throw new NameAfterVariableException();
        }
    }
    public static boolean isCorrectFormatFunction(String code) {
        String START = "void\\s+\\w+\\s*\\(";
        String PARAMETERS_FUNCTION_WITH_COMMA = "(\\s*final\\s+\\w+\\s+\\w+\\s*,\\s*|" +
                "\\s*\\w+\\s+\\w+\\s*,\\s*)*\\s*";
        String PARAMETER_FUNCTION_WITHOUT_COMMA = "(\\s*final\\s+\\w+\\s+\\w+\\s*|" +
                "\\s*\\w+\\s+\\w+\\s*)";
        String END = "\\)\\s*\\{\\s*$";

        String functionFormat_2 = START+
                                  PARAMETERS_FUNCTION_WITH_COMMA+
                                  PARAMETER_FUNCTION_WITHOUT_COMMA+
                                  END;
        Pattern functionP = Pattern.compile(functionFormat_2);
        Matcher functionM = functionP.matcher(code);
        return functionM.matches();


        //        if (startsWithPattern(code, STARTS_WITH_FINAL)) {
//            String secondPartOfCode = extractStringAfterWord(code, "final");
//            // we checked mixed because we want to check is there a variable
//            // not initialized in variable and throw there the specific exception
//            Pattern pattern = Pattern.compile(MIXED);
//            Matcher matcher = pattern.matcher(secondPartOfCode);
//            return matcher.matches();
//        }
//        return false;
    }
}
