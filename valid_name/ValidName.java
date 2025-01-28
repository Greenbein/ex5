package valid_name;

import valid_name.name_exceptions.*;

public class ValidName {
    /**
     * this function checks is a name of a variable is valid
     *  if not throwing relevant exception
     * @param name name of the variable
     * @return is a name of a variable is valid
     */
    public static boolean isValidVariableName(String name) {
        if (!name.matches("^\\w+$")) {
            // exception illegal format using invalid format
            throw new InvalidFormatNameException();
        }
        if (name.equals("_")) {
            // exception name is only _
            throw new NameIsUnderscoreException();
        }
        if (name.matches("^__.*$")) {
            // exception starts with double __
            throw new NameStartsWithDoubleUnderscoreException();
        }
        if (name.matches("^\\d{1}.*$")) {
            // exception starts with a number
            throw new NameStartsWithNumberException();
        }
        if (name.equals("int") || name.equals("double") || name.equals("char")
                || name.equals("String") || name.equals("boolean")) {
            throw new NameAfterVariableException();
        }
        if (name.equals("final")) {
            throw new InvalidNameFinalException();
        }
        return true;
    }

    /**
     * this function checks is a name of a method is valid
     * if not throwing relevant exception
     * @param name name of the method
     * @return is a name of a method is valid
     */
    public static boolean isValidMethodName(String name){
        if(!name.matches("^\\w+$")){
            // exception illegal format using invalid format
            throw new InvalidFormatNameException();
        }
        if(!name.matches("^[a-zA-Z].*")){
            // exception starts with a number
            throw new NameStartsNotWithALetterException();
        }
        if(name.equals("int")||name.equals("double")||name.equals("char")
                ||name.equals("String")||name.equals("boolean")){
            throw new NameAfterVariableException();
        }
        if(name.equals("final")){
            throw new InvalidNameFinalException();
        }
        return true;
    }
}
