package code_processing;

import code_processing.exceptions.invalidVariableTypeException;
import valid_name.ValidName;
import valid_name.name_exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodProcessing {
    private static final String METHOD_OPENING =  "void\\s+\\w+\\s*\\(";
    private static final String PARAMETERS_FUNCTION_WITH_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*,\\s*|\\s*\\w+\\s+\\w+\\s*,\\s*)*\\s*";
    private static final String PARAMETER_FUNCTION_WITHOUT_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*|\\s*\\w+\\s+\\w+\\s*)";
    private static final String METHOD_CLOSING = "\\)\\s*\\{\\s*$";
    private static final String STRING = "String";
    private static final String INTEGER = "int";
    private static final String DOUBLE = "double";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";

    /**
     *  this function would be used if first word of sentence is void in order
     *  to check is the function valid
     * @param line  line of the function we check
     * @return returns is the function valid or no
     */
    public boolean isCorrectFormatFunction(String line) {
        String functionFormat = METHOD_OPENING +
                PARAMETERS_FUNCTION_WITH_COMMA +
                PARAMETER_FUNCTION_WITHOUT_COMMA +
                METHOD_CLOSING;
        Pattern functionP = Pattern.compile(functionFormat);
        Matcher functionM = functionP.matcher(line);
        // check if it has correct format of a function
        if (!functionM.matches()) {
            throw new InvalidFormatNameException();
        }
        //valid method name
        String methodName = functionM.group(2);
        ValidName.isValidMethodName(methodName);
        String parametersWithComma = functionM.group(3);
        // If there are no parameters to process continue checking
        if (!(parametersWithComma == null) && !(parametersWithComma.trim().isEmpty())) {
            // split by commas (between different pairs of variable types and names)
            String[] variableTypesAndNames = parametersWithComma.split("\\s*,\\s*");
            // iterate on each type and variable name (one after each other)
            for (String variableTypeAndName : variableTypesAndNames) {
                // separate between variable type and name
                checkValidNameValidType(variableTypeAndName);
            }
        }
        String parametersWithoutAComma = functionM.group(4);
        checkValidNameValidType(parametersWithoutAComma);
        return true;
    }

    /**
     * this function checks is the name and type of variable valid
     * @param nameAndVariableType string that contains name and variable type in method
     */
    public void checkValidNameValidType(String nameAndVariableType) {
        String[] parts = nameAndVariableType.split("\\s+");
        String variableType = "";
        String variableName = "";
        if(parts.length == 2) {
            variableType = parts[0];
            variableName = parts[1];
        }
        else if(parts.length == 3){
            variableType = parts[1];
            variableName = parts[2];
        }
        isValidVariableType(variableType);
        ValidName.isValidVariableName(variableName);
    }

    /**
     * this function checks is the variable type valid
     * @param variableType the variable type
     */
    public void isValidVariableType(String variableType){
        if(!(variableType.equals(STRING)
                ||variableType.equals(CHAR)
                ||variableType.equals(INTEGER)
                ||variableType.equals(DOUBLE)
                ||variableType.equals(BOOLEAN))){
            throw new invalidVariableTypeException();
        }
    }
}
