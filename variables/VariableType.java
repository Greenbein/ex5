package variables;


import methods.exceptions.IncorrectParameterType;
import variables.exceptions.input_exceptions.IllegalTypeException;
import variables.variable_managers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this enum implements variable type for variable
 */
public enum VariableType{
    INT, DOUBLE, BOOLEAN, STRING, CHAR;
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    public static final String REGEX_INT = "\\s*[+-]?\\d+\\s*";
    public static final String REGEX_DOUBLE = "[+-]?(\\d*\\.\\d+|\\d+\\.\\d*)";
    public static final String REGEX_STRING = "^\"[^\"',\\\\]*\"$";

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
            throw new NullPointerException();
        }
        try {
            return VariableType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalTypeException(type);
        }
    }

    /**
     * Get the type of input
     * @param input - string with input
     * @return VariableType
     */
    public static VariableType getTypeOfInput(String input) {
        if(input.equals(TRUE)||input.equals(FALSE)){
            return VariableType.BOOLEAN;
        }
        Pattern patternInt = Pattern.compile(REGEX_INT);
        Matcher matcherInt = patternInt.matcher(input);
        if(matcherInt.matches()){
            return VariableType.INT;
        }

        Pattern patternDouble = Pattern.compile(REGEX_DOUBLE);
        Matcher matcherDouble = patternDouble.matcher(input);
        if(matcherDouble.matches()){
            return VariableType.DOUBLE;
        }

        Pattern patternString = Pattern.compile(REGEX_STRING);
        Matcher matcherString = patternString.matcher(input);
        if(matcherString.matches()){
            return VariableType.STRING;
        }

        return VariableType.CHAR;
    }

    public static boolean doesTypesMatchOneAnother
            (VariableType expectedType, VariableType receivedType) {
        if(expectedType == VariableType.BOOLEAN){
            if(receivedType == VariableType.CHAR ||
                    receivedType == VariableType.STRING ){
                return false;
            }
        }
        // double parameter can get only int or double
        else if(expectedType == VariableType.DOUBLE){
            if(receivedType != VariableType.DOUBLE &&
                    receivedType != VariableType.INT){
                return false;
            }
        }
        // int parameter can get only int or double
        else if(expectedType == VariableType.INT){
            if(receivedType != VariableType.INT &&
                    receivedType != VariableType.DOUBLE){
                return false;
            }
        }
        // String can get only String or char
        else if(expectedType == VariableType.STRING){
            if(receivedType != VariableType.STRING &&
                    receivedType != VariableType.CHAR){
                return false;
            }
        }
        // Char can get only char
        else if(expectedType == VariableType.CHAR){
            if(receivedType != VariableType.CHAR){
                return false;
            }
        }
        return true;
    }

}

