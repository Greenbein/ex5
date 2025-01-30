package code_processing;

import exceptions.basic_exceptions.condition_exceptions.*;
import databases.VariableDataBase;
import valid_name.ValidName;
import variables.Variable;
import variables.VariableType;
import exceptions.variables_exceptions.basic_variable_exception.UnreachableVariableException;
import variables.variable_managers.CharManager;
import variables.variable_managers.StringManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class process a line that contains a condition if/while
 */
public class ConditionProcessing {
    //----------------------------constants------------------------
    private static final String INPUT_FOR_CONDITION =
            "\\s*([+-]?(\\d*(\\d*\\.\\d*)?)|\\w+|true|false|\".*\"|\'.*\')\\s*";

    private static final String CORRECT_CONDITION_FORMAT =
            "\\(("+
            INPUT_FOR_CONDITION
                    + "\\s*(?:(\\&\\&)|(\\|\\|))\\s*"
            +INPUT_FOR_CONDITION
                    + ")*"+
            INPUT_FOR_CONDITION
                    + "\\s*\\)";

    public static final String CORRECT_WHILE_FORMAT =
            "\\s*while\\s*"+
                    CORRECT_CONDITION_FORMAT +
                    "\\s*\\{";

    public static final String CORRECT_IF_FORMAT =
            "\\s*if\\s*" +
            CORRECT_CONDITION_FORMAT +
                    "\\s*\\{";

    private static final String LINE_STARS_WITH_IF = "^\\s*if.*$";
    private static final String LINE_STARS_WITH_WHILE = "^\\s*while.*$";
    public static final String BRACKETS_FOR_INPUT = "\\((.*?)\\)";
    public static final String LOGIC_OPERATORS = "\\|\\||&&";
    //------------------privates---------------------
    private final VariableDataBase db;

    /**
     * default constructor condition processing
     * @param db database of all the variables we recorded
     */
    public ConditionProcessing(VariableDataBase db) {
        this.db = db;
    }

    /**
     * The function checks if the usage of command
     * while is correct (FORMAT ONLY)
     * @param code - given code
     * @param layer - current layer
     * @return true or false(exception)
     */
    public boolean isCorrectWhileFormat(String code, int layer) {
        if(layer==0){
            throw new IllegalScopeForIfException();
        }
        if(!code.matches(CORRECT_WHILE_FORMAT)){
            throw new InvalidFormatForWhileCommandException();
        }
        return true;
    }

    /**
     * this function checks is given the sentence starts with
     * while it is in the correct format if not throw exception
     * @param code given code from the file
     * @param layer the current layer it recorded in the code
     * @return is the code in a correct format of while
     */
    public boolean isCorrectWhileUsage(String code, int layer) {
        if(layer==0){
           throw new IllegalScopeForWhileException();
        }
        if(code.matches(CORRECT_WHILE_FORMAT)){
            checkConditionParameters(code,layer);
        }
        else{
            throw new InvalidFormatForWhileCommandException();
        }
        return false;
    }

    /**
     * The function checks if the usage of command
     * if is correct (FORMAT ONLY)
     * @param code - given code
     * @param layer - current layer
     * @return true or false(exception)
     */
    public boolean isCorrectIfFormat(String code, int layer) {
        if(layer==0){
            throw new IllegalScopeForIfException();
        }
        if(!code.matches(CORRECT_IF_FORMAT)){
            throw new InvalidFormatForIfCommandException();
        }
        return true;
    }

    /**
     * this function checks is given the sentence starts with
     * if it is in the correct format if not throw exception
     * @param code given code from the file
     * @param layer the current layer it recorded in the code
     * @return is the code in a correct format of if
     */
    public boolean isCorrectIfUsage(String code, int layer) {
        if(layer==0){
            throw new IllegalScopeForIfException();
        }
        if(code.matches(CORRECT_IF_FORMAT)){
            checkConditionParameters(code,layer);
        }
        else{
            throw new InvalidFormatForIfCommandException();
        }
        return false;
    }

    // this function checks the condition parameters if they are invalid
    // throw an exception if all the conditions valid return true
    private boolean checkConditionParameters(String code, int layer) {
        Pattern pattern = Pattern.compile(BRACKETS_FOR_INPUT);
        Matcher matcher = pattern.matcher(code);
        if(matcher.find()){
            String parameters = matcher.group(1);
            String[] parts = parameters.split(LOGIC_OPERATORS);
            for(String part : parts){
                checkParameterValidity(part,layer);
            }
        }
        return true;
    }

    // this function checks the validity pf a certain parameter
    private boolean checkParameterValidity(String parameter, int layer) {
        if(ValidName.isValidVarNameInput(parameter)){
            Variable variable = db.findVarByNameOnly(parameter,layer);
            if(variable != null){
                if (variable.getValueType().equals(VariableType.STRING)||
                        variable.getValueType().equals(VariableType.CHAR)){
                    throw new InvalidVarTypeForConditionException
                            (variable.getName(),variable.getValueType());
                }
                return true;
            }
            else{
                throw new UnreachableVariableException(parameter);
            }
        }
        else{
            StringManager stringManager = new StringManager();
            if(stringManager.isValidInput(parameter)){
                throw new InvalidVarTypeForConditionException(parameter,
                        VariableType.STRING);
            }
            CharManager charManager = new CharManager();
            if(charManager.isValidInput(parameter)){
                throw new InvalidVarTypeForConditionException(parameter,
                        VariableType.CHAR);
            }
            return true;
        }
    }

    /**
     * Check if the fist word in code is while
     * @param code - code
     * @return - boolean
     */
    public boolean isStartsWithWhile(String code){
        Pattern patternWhile = Pattern.compile(LINE_STARS_WITH_WHILE);
        Matcher mWhile = patternWhile.matcher(code);
        return mWhile.matches();

    }
    /**
     * Check if the fist word in code is if
     * @param code - code
     * @return - boolean
     */
    public boolean isStartsWithIf(String code){
        Pattern patternIf = Pattern.compile(LINE_STARS_WITH_IF);
        Matcher mIf = patternIf.matcher(code);
        return mIf.matches();

    }
}
