package code_processing;

import databases.VariableDataBase;
import valid_name.ValidName;
import variables.Variable;
import variables.VariableType;
import exceptions.variables_exceptions.basic_variable_exception.DoubleCreatingException;
import exceptions.variables_exceptions.basic_variable_exception.InvalidFinalVariableInitializationException;
import exceptions.variables_exceptions.basic_variable_exception.InvalidFormatException;
import exceptions.variables_exceptions.basic_variable_exception.UnreachableVariableException;
import exceptions.variables_exceptions.input_exceptions.SetValueTypeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for processing lines of code that include
 * type variables and names
 * of variables, while checking is the syntax legal,
 * checking the data types, and in which scopes.
 * It creates new variables,assign them value, it also updates variables
 * with values, and it also identifies if variable marked as final
 * not to assign new value and prevents double
 * initialization in the same layer of variable with the same name.
 */
public class RowProcessing {
    // ----------------------constants---------------------------------
    private static final String INPUT_REGEX =
        "('.*'|\".*\"|[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)";
    public static final String UPDATE_SINGLE_VAR_REGEX =
            "(\\w+)\\s*=\\s*" + INPUT_REGEX + "\\s*";
    private static final String TYPES_REGEX = "\\s*(int|double|String|boolean|char)";
    private static final String VAR_NAME_REGEX = "\\w+";
    private static final String EXTRACT_DATA_MIXED =
            "((int|double|String|boolean|char)|\\w+\\s*=\\s*" +
                    "('.*'|\".*\"|true|false|[+-]?((\\d*\\.\\d*)|\\d+)" +
                    "\\s*|\\w+)|\\w+)";
    private static final String STARTS_WITH_FINAL = "^\\s*final\\s.*";
    private static final String STARTS_WITH_VAR_TYPE =
            "^\\s*(int|double|String|boolean|char)\\s.*";
    private static final String MIXED =
            "^"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*,\\s*|"
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*|"
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*);$";
    private static final String INITIALIZED_ONLY = "^"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*);$";
    private static final String SETTING_ROW  =
            "\\s*(\\w+\\s*\\=\\s*('.*?'|\".*?\"|" +
                    "[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)\\,\\s*)*" +
                    "(\\w+\\s*\\=\\s*('.*?'|\".*?\"|" +
                    "[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)\\s*\\;\\s*)";
    private static final String WORD_FINAL = "final";
    private static final int TYPE_WORD_INDEX_ONE = 1;
    private static final int TYPE_WORD_INDEX_ZERO = 0;
    public static final String EQUALS = "=";
    // ------------------members of the class -----------------------------------
    private final VariableDataBase variableDataBase;

    /**
     * default constructor RowProcessing
     * @param variableDataBase given variable database
     */
    public RowProcessing(VariableDataBase variableDataBase) {
        this.variableDataBase = variableDataBase;
    }

    // this function extract string after a given word
    private String extractStringAfterWord(String code, String word) {
        int index = code.indexOf(word);
        if (index == -1) return code; // Return the original string if the word is not found
        return code.substring(index + word.length()).trim();
    }

    // this function checks is a code start with a certain pattern
    private boolean startsWithPattern(String code, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    //-----------------------Final-------------------------
    /**
     * this function checks is the format of a code that starts with final is
     * valid
     * @param code the code that starts with final
     * @return returns true if final and correct false if not final
     * and error if is final and incorrect format
     */
    public boolean isCorrectFormatFinal(String code) {
        if (startsWithPattern(code, STARTS_WITH_FINAL)) {
            String secondPartOfCode = extractStringAfterWord(code,WORD_FINAL);
            Pattern pattern = Pattern.compile(MIXED);
            Matcher matcher = pattern.matcher(secondPartOfCode);
            if(!matcher.matches()){
                throw new InvalidFormatException();
            }
            pattern = Pattern.compile(INITIALIZED_ONLY);
            matcher = pattern.matcher(secondPartOfCode);
            if(!matcher.matches()){
                throw new InvalidFinalVariableInitializationException();
            }
            return true;
        }
        return false;
    }

    //-------------------definition or initialization of variables------

    /**
     * this function checks is the format of initialization is mixed
     * so every variable can be initialized or not
     * @param code the code we checked
     * @return if it initialization and correct return true if
     * not initialization return false
     * if initialization that incorrect throw exception
     */
    public boolean isMixed(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(MIXED);
            Matcher matcher = patternMixed.matcher(code);
            if(!matcher.matches()){
                throw new InvalidFormatException();
            }
            return true;
        }
        return false;
    }

    /**
     * this function checks is a set row is valid
     * @param code the code we check is the set row valid
     * @return if set row valid return true else throw exception
     */
    public boolean isSettingRow(String code) {
        Pattern pattern = Pattern.compile(SETTING_ROW);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    // -------------------extract data final -----------------------------

    /**
     * this function extracts variables into the database in the case
     * the line starts with final
     * @param code the code we got from the file
     * @param currentLayer the current layer we are going add the variable into
     * @param currentLine the number of the current line
     */
    public void extractDataFinal(String code,
                                 int currentLayer,
                                 int currentLine) {
        extractNewVarsFromRow(code,
                true,
                TYPE_WORD_INDEX_ONE,
                currentLayer,
                currentLine);
    }
    // -------------------extract data mixed -----------------------------

    /**
     * this function extracts variables into the database in the case
     * line starts with initializer (boolean/char/String/double/int)
     * @param code the code we got from the file
     * @param currentLayer the current layer we are going add the variable into
     * @param currentLine the number of the current line
     */
    public void extractDataMixed(String code,
                                 int currentLayer,
                                 int currentLine) {
        extractNewVarsFromRow(code,
                false,
                TYPE_WORD_INDEX_ZERO,
                currentLayer,currentLine);
    }
    // ------------------------- update variables -------------------------------

    /**
     * his function gets the code and the layer we want to add the
     * @param code - code with setting format
     * @param currentLayer - current layer
     */
    public void updateVariablesValues(String code, int currentLayer) {
        System.out.println(code);
        Pattern pattern = Pattern.compile(EXTRACT_DATA_MIXED);
        Matcher matcher = pattern.matcher(code);
        ArrayList<String> subStrings = new ArrayList<>();
        while (matcher.find()) {
            subStrings.add(matcher.group());
        }
        for (int i = 0; i < subStrings.size(); i++) {
            updateVar(subStrings.get(i), currentLayer);
        }
    }

    //  This method processes an assign to variable code,first it extracts
    //  the variable name and its new value, and updates
    //  the variable with this name in the database.If the variable is not
    //  found in the DB, an exception is thrown.
    private void updateVar( String code, int layer) {
        code = code.trim();
        if (code.contains(EQUALS)) {
            Pattern pattern = Pattern.compile(UPDATE_SINGLE_VAR_REGEX);
            Matcher matcher = pattern.matcher(code);
            if (matcher.matches()) {
                String varName = matcher.group(1).trim(); // Captures the variable name
                // Captures the value, preserving formatting
                String value = matcher.group(2).trim();
                Variable variable =
                        this.variableDataBase.findVarByNameOnly(varName, layer);
                if (variable == null) {
                    throw new UnreachableVariableException(varName);
                }
                if(ValidName.isValidVarNameInput(value)){
                    Variable variableOther =
                            variableDataBase.findVarByNameOnly(value, layer);
                    if (variableOther == null) {
                        throw new UnreachableVariableException(value);
                    }
                    VariableType expectedType = variable.getValueType();
                    VariableType receivedType = variableOther.getValueType();
                    boolean typeStatus =
                            VariableType.doesTypesMatchOneAnother(expectedType, receivedType);
                    if(!typeStatus){
                        throw new SetValueTypeException(varName,expectedType,receivedType);
                    }
                    variable.setValue(variableOther.getValue());
                }
                else{
                    variable.setValue(value);
                }
            }
        } else {
            String varName = code;
            Variable variable =
                    this.variableDataBase.findVarByNameOnly(varName, layer);
            if(variable == null){
                throw new UnreachableVariableException(varName);
            }
            else{
                throw new InvalidFormatException();
            }
        }
    }

    //--------------------extract data from string -----------------
    // this function finds patterns of variable declaration and assignment extract them,
    // and adds the variables with those values to the database.
    private void extractNewVarsFromRow(String code,
                                       boolean isFinal,
                                       int typeWordIndex,
                                       int currentLayer,
                                       int currentLine) {
        Pattern pattern = Pattern.compile(EXTRACT_DATA_MIXED);
        Matcher matcher = pattern.matcher(code);
        ArrayList<String> subStrings = new ArrayList<>();
        while (matcher.find()) {
            subStrings.add(matcher.group());
        }
        String typeWord = subStrings.get(typeWordIndex);
        if(typeWordIndex>0){
            subStrings.remove(typeWordIndex-1);
        }
        addNewVars(typeWord,subStrings,currentLayer,isFinal,this.variableDataBase);
    }

    // this functionAdds new variables to the database based
    // on a list of Strings of variable declarations.
    private void addNewVars(String typeStr,
                                            ArrayList<String> subStrings,
                                            int layer, boolean isFinal,
                                            VariableDataBase variableDataBase){
        VariableType type = VariableType.fromString(typeStr);
        for (int i = 1; i < subStrings.size(); i++) {
            createVariable(type, subStrings.get(i), layer, isFinal,variableDataBase);
        }
    }

    // Creates a new variable and adds it to the database if no variable with the same
    // name exists in the same scope. Throws an exception if a duplicate is found.
    private void createVariable(VariableType type,
                                String code,
                                int layer,
                                boolean isFinal,
                                VariableDataBase variableDataBase) {
        code = code.trim();
        if (code.contains(EQUALS)) {
            String[] subWords = code.split(EQUALS);
            String varName = subWords[0].trim();
            String value = subWords[1].trim();
            Variable other= this.variableDataBase.
                    findVarByNameOnly(varName,layer);
            if(other!=null){
                if(other.getLayer()==layer){
                    throw new DoubleCreatingException(varName);
                }
            }
            Variable variable = new Variable(varName,layer,
                    isFinal,true,type, value,this.variableDataBase);
            variableDataBase.addVariable(variable);
        } else {
            String varName = code;
            Variable variable = new Variable(varName,
                    layer,isFinal,type,this.variableDataBase);
            variableDataBase.addVariable(variable);
        }
    }
}
