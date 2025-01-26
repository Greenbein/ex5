package row_processing;

import row_processing.exceptions.*;
import variables.exceptions.var_name_exceptions.NameAfterVariableException;

public class RowValidations {
    private void checkSlash(String text, int lineNumber) {
        // empty line return
        if(text.strip().isEmpty()){
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
}
