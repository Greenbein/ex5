package code_processing;

import exceptions.basic_exceptions.InvalidFormatFunctionException;
import exceptions.basic_exceptions.invalidVariableTypeException;
import databases.MethodsDataBase;
import databases.VariableDataBase;
import methods.Method;
import exceptions.methods_exceptions.*;
import valid_name.ValidName;
import variables.Variable;
import variables.VariableType;
import exceptions.variables_exceptions.basic_variable_exception.UnreachableVariableException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class handles the syntax problems of initialization of a function
 */
public class MethodProcessing {
    private static final String METHOD_OPENING =  "void\\s+(\\w+)\\s*\\(";
    private static final String PARAMETERS_FUNCTION_WITH_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*," +
                    "\\s*|\\s*\\w+\\s+\\w+\\s*,\\s*)*\\s*";
    private static final String PARAMETER_FUNCTION_WITHOUT_COMMA =
            "(\\s*final\\s+\\w+\\s+\\w+\\s*|\\s*\\w+\\s+\\w+\\s*)";
    private static final String METHOD_CLOSING = "\\)\\s*\\{\\s*$";
    private static final String EMPTY_WITHOUT_VARIABLES =
            "void\\s+(\\w+)\\s*\\(\\)\\s*\\{\\s*$";
    private static final String FUNCTION_USAGE_FORMAT =
            "\\s*\\w+\\s*\\(((('.*'|\".*\"|[+-]?((\\d*\\.\\d*)|\\d+)|" +
                    "\\w+|true|false)\\,\\s*)*('.*'|\".*\"|" +
                    "[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)|\\s*)\\s*\\)\\s*\\;";
    private static final String STRING = "String";
    private static final String INTEGER = "int";
    private static final String DOUBLE = "double";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final String FUNCTION_PARAMETERS_REGEX = "(final\\s+)?\\w+\\s+\\w+";
    private static final String BRACKETS_FOR_INPUT = "\\((.*?)\\)";
    private static final String VOID_METHOD_DECLARATION = "^\\s*void\\s+(\\w+)\\s*\\(";
    private static final String MATCH_METHOD_PARAMETERS_REGEX =
            "\\s*(final\\s+)?(int|String|char|double|boolean)\\s+(\\w+)\\s*";
    private static final String DEFAULT = "default value";
    public static final String METHOD_BEGINNING_REGEX = "\\s*\\w+\\s*\\(.*";
    public static final String MATCH_METHOD_NAME_REGEX = "\\s*(\\w+)\\s*\\(";
    public static final String INPUT_REGEX =
            "\\s*('.*'|\".*\"|[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)\\s*";


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
            throw new InvalidFormatFunctionException();
        }
        //valid method name
        String methodName = functionM.group(1);
        ValidName.isValidMethodName(methodName);
        String parametersWithComma = functionM.group(2);
        // If there are no parameters to process continue checking
        if (parametersWithComma != null && !(parametersWithComma.trim().isEmpty())) {
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

    // in the case the row supports method declaration format
    // we add a new method to methods database
    public void processFunctionDeclaration(String code, MethodsDataBase mdb) {
        checkIfTheMethodAlreadyExists(code,mdb);
        String name = getNewMethodName(code);
        ArrayList<VariableType>methodParametersTypes =
                extractFunctionParametersTypes(code);
        Method newMethod = new Method(name, methodParametersTypes);
        mdb.addMethod(newMethod);
    }


    /**
     * add method parameters to variables dataBase with default
     * we use it in order to check in next lines if we use a variable
     * from method's input the method return ArrayList of variables
     * with default values (we need to create a new Method and add it
     * to methods dataBase)
     * @param input
     * @param db
     */
    public void loadFunctionParametersToDB(String input, VariableDataBase db) {
        // We do it when we know that the input is valid for function
        // Capture text inside ( )
        Pattern pattern = Pattern.compile(BRACKETS_FOR_INPUT);
        Matcher matcher = pattern.matcher(input);
        // Ensure there is a match before calling .group(1)
        if (matcher.find()) {
            String parameters = matcher.group(1).trim();
            // Separate the gotten parameters into different substrings
            List<String> parametersList = convertFunctionParametersToList
                    (parameters, FUNCTION_PARAMETERS_REGEX);
            // Declare an ArrayList<Variable> for the future method
            for (String currentParameter : parametersList) {
                // Create a new variable with default value
                Variable parameter = createFunctionParameter
                        (currentParameter,db,getNewMethodName(input));
                if(parameter!=null){
                    db.addVariable(parameter);
                }
//                else{ // check maybe delete later
//                    throw new
//                    RuntimeException("Failed to create a new parameter for the method");
//                }
            }
        } else {
             throw new InvalidFormatFunctionException();
        }
    }

    // check if the received method name already exists
    private void checkIfTheMethodAlreadyExists(String code, MethodsDataBase mDB){
        String methodName = getMethodNameFromCode(code);
        boolean exists = mDB.isExist(methodName);
        if(exists){
            throw new DoubleFunctionDeclaration(methodName);
        }
    }

    // the method extract parameters types during function declaration
    // is saves it in ArrayList<VariableType> and then returns it
    // we will use this ArrayList in order to create a new Method and save is methods database
    private ArrayList<VariableType> extractFunctionParametersTypes(String input) {
        // We do it when we know that the input is valid for function
        // Capture text inside ( )
        Pattern pattern = Pattern.compile(BRACKETS_FOR_INPUT);
        Matcher matcher = pattern.matcher(input);
        // Ensure there is a match before calling .group(1)
        if (matcher.find()) {
            String parameters = matcher.group(1).trim();
            // Separate the gotten parameters into different substrings
            List<String> parametersList = convertFunctionParametersToList
                    (parameters,FUNCTION_PARAMETERS_REGEX);

            // Declare an ArrayList<Variable> for the future method
            ArrayList<VariableType>methodParametersTypes = new ArrayList<>();
            for (String currentParameter : parametersList) {
                // Create a new variable with default value
                VariableType parameterType =
                        extractParameterType(currentParameter,getNewMethodName(input));
                if(parameterType!=null){
                    methodParametersTypes.add(parameterType);
                }
                else{
                    throw new RuntimeException("Failed to extract the parameter for the method");
                }
            }
            return methodParametersTypes;
        } else {
            throw new InvalidFormatFunctionException();
        }
    }

    // extract a new function name from function declaration row
    private String getNewMethodName(String code){
        Pattern pattern = Pattern.compile(VOID_METHOD_DECLARATION);
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) { // Use find() instead of matches() to search within the string
            String funcName = matcher.group(1);// Group 1 contains the function name
            boolean ok = ValidName.isValidMethodName(funcName);
            if(ok){
                return funcName;
            }
        } else {
            return null;
        }
        return null;
    }

    // Separate the gotten parameters into different substrings
    private List<String> convertFunctionParametersToList(String input, String regex) {
        List<String> paramList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            paramList.add(matcher.group().strip());
        }
        return paramList;
    }

    // Create a new variable with default value
    // We use its type in order to check function calling correctness (in the future)
    private Variable createFunctionParameter(String input, VariableDataBase db, String methodName) {
        // Regular expression to match optional "final", the type, and the variable name
        Pattern pattern = Pattern.compile(MATCH_METHOD_PARAMETERS_REGEX);
        Matcher matcher = pattern.matcher(input.strip()); // Use strip() to remove leading/trailing spaces

        if (matcher.matches()) {
            boolean isFinal = matcher.group(1) != null; // Check if "final" exists
            String type = matcher.group(2).strip();// Extract type (int, String, etc.)
            String variableName = matcher.group(3).strip();// Extract variable name
            if(ValidName.isValidVarNameInput(variableName)){
                return new Variable
                        (variableName,
                                1,
                                isFinal,
                                true,
                                VariableType.fromString(type),
                                DEFAULT,
                                db);
            }
        } else {
            throw new InvalidInputForMethodDeclarationException(methodName);
        }
        return null;
    }

    private VariableType extractParameterType(String input, String methodName) {
        // Regular expression to match optional "final", the type, and the variable name
        Pattern pattern = Pattern.compile(MATCH_METHOD_PARAMETERS_REGEX);
        Matcher matcher = pattern.matcher(input.strip());
        if (matcher.matches()) {
            String type = matcher.group(2).strip();
            return VariableType.fromString(type);
        } else {
            throw new InvalidInputForMethodDeclarationException(methodName);
        }
    }



    // ------------------------ Method usage case ------------------------------

    /**
     * The method check if the row starts with \s*func_name\s*(...
     * @param code
     * @return - true or false
     */
    public boolean isMethodUsage(String code, ConditionProcessing c){
        Pattern pattern = Pattern.compile(METHOD_BEGINNING_REGEX);
        Matcher matcher = pattern.matcher(code);
        boolean isWhile = c.isStartsWithWhile(code);
        boolean isIf = c.isStartsWithIf(code);
        if(matcher.find() && !isWhile && !isIf){
           return true;
        }
        return false;
    }
    /**
     * The method check if the format of row matches to calling method case, for instance foo(a,1);
     * @param code - code from a gotten row
     * @return - true if everything is correct
     */
    public boolean isCorrectMethodUsageFormat(String code){
        Pattern pattern = Pattern.compile(FUNCTION_USAGE_FORMAT);
        Matcher matcher = pattern.matcher(code);
        if(!matcher.matches()){
            throw new IncorrectFunctionCallingFormat();
        }
        return true;
    }

    // check if calling the method is legal
    public void checkMethodUsageCorrectness(String code,
                                            VariableDataBase variableDataBase,
                                            MethodsDataBase methodsDataBase){
        String methodName = getMethodNameFromCode(code);
        ifMethodExistsInDataBase(methodName, methodsDataBase);
        Method myMethod = methodsDataBase.getMethod(methodName);
        checkParametersForCallingFunction(code,variableDataBase,myMethod);
    }

    // check if method name exists in method database
    // if it does not -> the method name is not correct -> Exception
    private void ifMethodExistsInDataBase(String methodName,
                                          MethodsDataBase methodsDataBase){
        boolean ok = methodsDataBase.isExist(methodName);
        if(!ok){
            throw new MethodDoesNotExistException(methodName);
        }
    }

    // get method from the DB according to its name
    // It is not allowed to add methods with the same names
    private String getMethodNameFromCode(String code){
        Pattern pattern = Pattern.compile(MATCH_METHOD_NAME_REGEX);
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {// Use find to locate the match
            String funcName = matcher.group(1); // Group 1 contains the function name
            return funcName;
        }
        return null;
    }

    // check if the provided parameters are correct for the method
    private boolean checkParametersForCallingFunction(String code,
                                                      VariableDataBase variableDataBase,
                                                      Method myMethod){
        // Capture the code inside ()
        Pattern pattern = Pattern.compile(BRACKETS_FOR_INPUT);
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            // Save the captured code as String variable
            String parameters = matcher.group(1).trim();
            // Strip the text in (), i.e. get an ArrayList of parameters
            List<String> parametersList = convertFunctionParametersToList
                    (parameters, INPUT_REGEX);
            // Get real parameters of the method (with default values)
            ArrayList<VariableType> realMethodParameters = myMethod.getParameterTypes();
            if(realMethodParameters.size() != parametersList.size()){
                throw new IncorrectNumberOfParameters
                        (myMethod.getName(),realMethodParameters.size(),parametersList.size());
            }
            for(int i=0;i<realMethodParameters.size();i++){
                // check the type correctness if parameter is a reference to variable
                VariableType expectedTypeI = realMethodParameters.get(i);
                Variable varFromDB = checkIfParamIsValidVarName
                        (parametersList.get(i),variableDataBase,expectedTypeI,i,myMethod.getName());
                //if the varFromDB is not null -> there were no exceptions -> parametersList.get(i)
                // is legal variable name, the variable exists in the dataBase
                // and its type match the expected one
                // If it is null -> in the code was a logic problem
                if(varFromDB == null){
                    // check the type correctness if parameter is a value
                    String value = parametersList.get(i).trim();
                    checkIfParamIsValueInput(value,expectedTypeI,i,myMethod.getName());
                }
                // check the type correctness if parameter is a value
            }
            return true;
        }
        else{
            throw new InvalidFormatFunctionException();
        }
    }

    // check parameter's type if it is variable name
    private Variable checkIfParamIsValidVarName
    (String potentialVaName,VariableDataBase variableDataBase,VariableType expectedTypeI, int index, String methodName){
        if(ValidName.isValidVarNameInput(potentialVaName)){
            Variable myVar = variableDataBase.findVarByNameOnly(potentialVaName,0);
            if(myVar == null){
                throw new UnreachableVariableException(potentialVaName);
            }
            else{
                VariableType receivedTypeI = myVar.getValueType();
                if(!receivedTypeI.equals(expectedTypeI)){
                    throw new IncorrectParameterType(index+1,methodName,expectedTypeI,receivedTypeI);
                }
                else{
                    return myVar;
                }
            }
        }
        return null;
    }

    // check parameter's type  if it is value and not variable name
    private void checkIfParamIsValueInput(String input,VariableType expectedTypeI, int index, String methodName){
        // get input's type according to input's value
        VariableType receivedTypeI = VariableType.getTypeOfInput(input);
        // boolean parameter can't get String or char, however can get boolean,int or double
        boolean typeStatus = VariableType.doesTypesMatchOneAnother(expectedTypeI,receivedTypeI);
        if (!typeStatus) {
            throw new IncorrectParameterType(index+1,methodName,expectedTypeI,receivedTypeI);
        }

    }
}
