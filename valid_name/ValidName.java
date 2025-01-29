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
    public static boolean isValidVarNameInput(String name) {
        // Check if the name matches the valid format (alphanumeric + underscores)
        if (!name.matches("^\\w+$")) {
            return false;
        }

        // Check if the name is "_"
        if (name.equals("_")) {
            return false;
        }

        // Check if the name starts with "__"
        if (name.matches("^__.*$")) {
            return false;
        }

        // Check if the name starts with a digit
        if (name.matches("^\\d.*$")) {
            return false;
        }

        // Check if the name is a reserved keyword for variable types
        if (name.equals("int") || name.equals("double") || name.equals("char") ||
                name.equals("String") || name.equals("boolean")) {
            return false;
        }

        // Check if the name is "final"
        if (name.equals("final")) {
            return false;
        }
        if (name.equals("true") || name.equals("false")){
            return false;
        }

        // All checks passed
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
