package variables.variable_managers;

import variables.Variable;
import variables.exceptions.input_exceptions.InvalidBooleanException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for boolean variables,
 * initialize and update them
 */
public class BooleanManager implements ManagerInterface<Boolean> {
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private final IntegerManager integerManager;
    private final DoubleManager doubleManager;
    private final Variable variable;
    private String valueTypeForBoolean;

    /**
     * Constructor for BooleanManager
     * @param integerManager - manager for Integer
     * @param doubleManager - manager for Double
     * @param variable - the variable we work with
     */
    public BooleanManager(IntegerManager integerManager, DoubleManager doubleManager, Variable variable) {
        this.integerManager = integerManager;
        this.doubleManager = doubleManager;
        this.variable = variable;
    }
    // checks is String input represents a boolean
    private boolean checkIsInputBoolean(String input){
        Pattern truePattern = Pattern.compile("^true$");
        Matcher matcherBoolean = truePattern.matcher(input);
        if(matcherBoolean.matches()){
            this.setValueTypeForBoolean(TRUE);
            return true;
        }
        Pattern falsePattern = Pattern.compile("^false$");
        matcherBoolean = falsePattern.matcher(input);
        if(matcherBoolean.matches()){
            this.setValueTypeForBoolean(FALSE);
            return true;
        }
        return false;
    }

    // switches a string that represents integer into a boolean
    private boolean switchIntegerToBoolean(String input){
        int boolIntValue = this.integerManager.extractValue(input);
        return boolIntValue != 0;
    }

    // switches a string that represents double into a boolean
    private boolean switchDoubleToBoolean(String input){
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        double boolDoubleValue = Double.parseDouble(cleanedInput);
        return boolDoubleValue != 0;
    }

    // switches a string that represents boolean into a boolean
    private boolean switchBooleanToBoolean(String input){
        return input.equals(TRUE);
    }

    /**
     * this function returns is the string represents an input of a boolean
     * @param input the input of the variable
     * @return  is the input represents an input of a boolean
     */
    @Override
    public boolean isValidInput(String input) {
        if(this.integerManager.isValidInput(input)){
            this.setValueTypeForBoolean(INTEGER);
            return true;
        }
        if(this.doubleManager.isValidInput(input)){
            this.setValueTypeForBoolean(DOUBLE);
            return true;
        }
        return checkIsInputBoolean(input);
    }

    /**
     * Extract boolean value from input string (when the input is already verified)
     * @param input - String with input
     * @return - boolean variable
     */
    @Override
    public Boolean extractValue(String input) {
        if(this.isValidInput(input)){
             switch (this.getValueTypeForBoolean()) {
                case INTEGER :
                    return switchIntegerToBoolean(input);
                case DOUBLE :
                    return switchDoubleToBoolean(input);
                default :
                    return switchBooleanToBoolean(input);
            }
        }
        else{
            throw new InvalidBooleanException(this.variable.getName());
        }
    }

    // set a new valueTypeForBoolean
    private void setValueTypeForBoolean(String valueTypeForBoolean) {
        this.valueTypeForBoolean = valueTypeForBoolean;
    }

    // get valueTypeForBoolean
    private String getValueTypeForBoolean(){
        return this.valueTypeForBoolean;
    }
}
