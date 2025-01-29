package valid_name;

import valid_name.name_exceptions.*;

/**
 * this class checks the validity of the name
 */
public class ValidName {
    private static final String INTEGER= "int";
    private static final String CHARACTER= "char";
    private static final String STRING= "string";
    private static final String DOUBLE= "double";
    private static final String BOOLEAN= "boolean";
    private static final String FINAL= "final";
    private static final String TRUE= "true";
    private static final String FALSE= "false";
    private static final String NAME_IS_A_WORD= "^\\w+$";
    private static final String NAME_STARTS_WITH_DOUBLE_UNDERSCORE= "^__.*$";
    private static final String NAME_STARTS_WITH_A_NUMBER= "^\\d{1}.*$";
    private static final String NAME_STARTS_WITH_A_LETTER= "^[a-zA-Z].*";

    /**
     * this function checks is a name of a variable is valid
     *  if not throwing relevant exception
     * @param name name of the variable
     * @return is a name of a variable is valid
     */
    public static boolean isValidVariableName(String name) {
        if (!name.matches(NAME_IS_A_WORD)) {
            // exception illegal format using invalid format
            throw new InvalidFormatNameException();
        }
        if (name.equals("_")) {
            // exception name is only _
            throw new NameIsUnderscoreException();
        }
        if (name.matches(NAME_STARTS_WITH_DOUBLE_UNDERSCORE)) {
            // exception starts with double __
            throw new NameStartsWithDoubleUnderscoreException();
        }
        if (name.matches(NAME_STARTS_WITH_A_NUMBER)) {
            // exception starts with a number
            throw new NameStartsWithNumberException();
        }
        return validityChecksNameAsVariables(name);
    }

    /**
     * this function checks if a given input is a valid name of a variable
     * @param name name of the variable
     * @return is a name of a an input is valid
     */
    public static boolean isValidVarNameInput(String name) {
        // Check if the name matches the valid format (alphanumeric + underscores)
        if (!name.matches(NAME_IS_A_WORD)) {
            return false;
        }
        // Check if the name is "_" or  starts with "__" or starts with a digit
        if (name.equals("_")||name.matches(NAME_STARTS_WITH_DOUBLE_UNDERSCORE)
                ||name.matches(NAME_STARTS_WITH_A_NUMBER)) {
            return false;
        }
        // Check if the name is a reserved keyword for variable types
        if (name.equals(INTEGER) || name.equals(DOUBLE) || name.equals(CHARACTER) ||
                name.equals(STRING) || name.equals(BOOLEAN)) {
            return false;
        }
        // Check if the name is "final" true or false
        if (name.equals(FINAL)||name.equals(TRUE)||name.equals(FALSE)) {
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
        if(!name.matches(NAME_IS_A_WORD)){
            // exception illegal format using invalid format
            throw new InvalidFormatNameException();
        }
        if(!name.matches(NAME_STARTS_WITH_A_LETTER)){
            // exception starts with a number
            throw new NameStartsNotWithALetterException();
        }
        return validityChecksNameAsVariables(name);
    }

    // set of words that name can't be
    private static boolean validityChecksNameAsVariables(String name) {
        if(name.equals(INTEGER)||name.equals(DOUBLE)||name.equals(CHARACTER)
                ||name.equals(STRING)||name.equals(BOOLEAN)){
            throw new NameAfterVariableException();
        }
        if(name.equals(FINAL)){
            throw new InvalidNameFinalException();
        }
        if(name.equals(TRUE) || name.equals(FALSE)){
            throw new InvalidNameTrueFalseException();
        }
        return true; // name of method valid
    }
}
