package variables;


import variables.exceptions.input_exceptions.IllegalTypeException;
import variables.variable_managers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this enum implements variable type for variable
 */
public enum VariableType{
    INT, DOUBLE, BOOLEAN, STRING, CHAR;

    /**
     *
     * @return this function returns lower case of enum
     */
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * this functions switches type to enum
     * @param type type of variable
     * @return enum variable type
     */
    public static VariableType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        try {
            return VariableType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid VariableType: " + type);
        }
    }

    /**
     * Get the type of input
     * @param input - string with input
     * @return VariableType
     */
    public static VariableType getTypeOfInput(String input) {
        if(input.equals("true")||input.equals("false")){
            return VariableType.BOOLEAN;
        }
        Pattern patternInt = Pattern.compile("\\s*[+-]?\\d+\\s*");
        Matcher matcherInt = patternInt.matcher(input);
        if(matcherInt.matches()){
            return VariableType.INT;
        }

        Pattern patternDouble = Pattern.compile("[+-]?(\\d*\\.\\d+|\\d+\\.\\d*)");
        Matcher matcherDouble = patternDouble.matcher(input);
        if(matcherDouble.matches()){
            return VariableType.DOUBLE;
        }

        Pattern patternString = Pattern.compile("^\"[^\"',\\\\]*\"$");
        Matcher matcherString = patternString.matcher(input);
        if(matcherString.matches()){
            return VariableType.STRING;
        }

        return VariableType.CHAR;
    }

//    /**
//     * Check whether the provided type is legal
//     * @param type - gotten type
//     * @return boolean
//     */
//    public static boolean checkIfCorrectType(String type) {
//        System.out.println("TYYYYYYYYYPE: "+type);
//        Pattern pattern = Pattern.compile("^(int|double|String|boolean|char)$");
//        Matcher matcher = pattern.matcher(type);
//        return matcher.matches();
//    }
}

