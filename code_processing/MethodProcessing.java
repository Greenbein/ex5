package code_processing;

import code_processing.exceptions.InvalidFormatFunctionException;
import code_processing.exceptions.invalidVariableTypeException;
import databases.VariableDataBase;
import valid_name.ValidName;
import valid_name.name_exceptions.*;
import variables.Variable;
import variables.VariableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class handles the syntax problems of initialization of a function
 */
public class MethodProcessing {
    private static final String METHOD_OPENING =  "void\\s+(\\w+)\\s*\\(";
    private static final String PARAMETERS_FUNCTION_WITH_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*,\\s*|\\s*\\w+\\s+\\w+\\s*,\\s*)*\\s*";
    private static final String PARAMETER_FUNCTION_WITHOUT_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*|\\s*\\w+\\s+\\w+\\s*)";
    private static final String METHOD_CLOSING = "\\)\\s*\\{\\s*$";
    private static final String EMPTY_WITHOUT_VARIABLES =
            "void\\s+(\\w+)\\s*\\(\\)\\s*\\{\\s*$";
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
        // case we have no variables
        Pattern patternWithoutVariables = Pattern.compile(EMPTY_WITHOUT_VARIABLES);
        Matcher functionWithoutVariables = patternWithoutVariables.matcher(line);
        if(functionWithoutVariables.matches()) {
            String methodName = functionWithoutVariables.group(1);
            ValidName.isValidMethodName(methodName);
            return true;
        }
        // case with variables
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
        String methodName = functionM.group(1);
        ValidName.isValidMethodName(methodName);
        String parametersWithComma = functionM.group(2);
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
        String parametersWithoutAComma = functionM.group(3);
        checkValidNameValidType(parametersWithoutAComma);
        return true;
    }

    /**
     * this function checks is the name and type of variable valid
     * @param nameAndVariableType string that contains name and variable type in method
     */
    public void checkValidNameValidType(String nameAndVariableType) {
        nameAndVariableType = nameAndVariableType.trim();
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
            throw new invalidVariableTypeException(variableType);
        }
    }

    public void extractFunctionParametersToDB(String input, VariableDataBase db) {
        // We do it when we know that the input is valid for function
        Pattern pattern = Pattern.compile("\\((.*?)\\)"); // Capture text inside ( )
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) { // Ensure there is a match before calling .group(1)
            String parameters = matcher.group(1).trim();
            List<String> parametersList = convertFunctionParametersToList(parameters);

            for (String currentParameter : parametersList) {
                addToFunctionLayer(currentParameter, db);
            }
        } else {
             throw new InvalidFormatFunctionException();
        }
    }


    private List<String> convertFunctionParametersToList(String input) {
        List<String> paramList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(final\\s+)?\\w+\\s+\\w+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            paramList.add(matcher.group().strip());
        }

        return paramList;
    }

    private void addToFunctionLayer(String input, VariableDataBase db){
        // Regular expression to match optional "final", the type, and the variable name
        String regex = "\\s*(final\\s+)?(int|String|char|double|boolean)\\s+(\\w+)\\s*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input.strip()); // Use strip() to remove leading/trailing spaces

        if (matcher.matches()) {
            boolean isFinal = matcher.group(1) != null; // Check if "final" exists
            String type = matcher.group(2).strip(); // Extract type (int, String, etc.)
            String variableName = matcher.group(3).strip();// Extract variable name
            if(ValidName.isValidVarNameInput(variableName)){
                Variable newVar =  new Variable(variableName,1,isFinal,true,VariableType.fromString(type),"default value",db);
                db.addVariable(newVar);
            }
        } else {
            throw new InvalidFormatNameException();
        }
    }

//    public static void main(String[] args) {
//        VariableDataBase db = new VariableDataBase();
//        String code = "void hello(final int a,char b, final String c){";
//        MethodProcessing methodProcessing = new MethodProcessing();
//        if(methodProcessing.isCorrectFormatFunction(code)){
//            methodProcessing.extractFunctionParametersToDB(code,db);
//        }
//        System.out.println(db);
//    }

}
