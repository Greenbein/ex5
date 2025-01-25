package variables.boolean_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.boolean_s_java.exceptions.InvalidBooleanException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements the variable boolean at java
 */
public class BooleanSJava extends Variable {
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private boolean value;
    private String valueType;

    /**
     * constructor of initialized boolean variable
     * @param name of boolean variable
     * @param layer of boolean variable
     * @param isFinal is the boolean variable final
     * @param value of boolean variable
     */
    public BooleanSJava(String name, int layer, boolean isFinal,String value) {
        super(name, layer, isFinal, true);
        initializeValue(value);
    }

    /**
     * constructor of non initialized boolean variable
     * @param name of boolean variable
     * @param layer of boolean variable
     * @param isFinal is the boolean variable final
     */
    public BooleanSJava(String name, int layer, boolean isFinal) {
        super(name, layer, isFinal, false);
    }

    /**
     * this function returns is the string represents an input of a boolean
     * @param input the input of the variable
     * @return  is the input represents an input of a boolean
     */
    @Override
    public boolean isValidInput(String input) {
        if(checkIsInputInteger(input)){return true;}
        if(checkIsInputDouble(input)){return true;}
        return checkIsInputBoolean(input);
    }

    /**
     * this function set the value of a boolean variable
     * @param input the value we need to set
     */
    @Override
    public void setValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        updateValue(input);
    }

    /**
     * this function initializes the value of a boolean variable
     * @param input the value we need to implement in the initializes variable
     */
    @Override
    public void initializeValue(String input) {
       updateValue(input);
    }

    // checks is String input represents a boolean
    private boolean checkIsInputBoolean(String input){
        Pattern truePattern = Pattern.compile("^true$");
        Matcher m = truePattern.matcher(input);
        if(m.matches()){
            this.valueType = TRUE;
            return true;
        }
        Pattern falsePattern = Pattern.compile("^false$");
        m = falsePattern.matcher(input);
        if(m.matches()){
            this.valueType = FALSE;
            return true;
        }
        return false;
    }

    // checks is String input represents a double (valid input of boolean)
    private boolean checkIsInputDouble(String input){
        Pattern doublePattrern = Pattern.compile("^[+-]?(\\d*.\\d+|^[+-]?\\d+.\\d*)$");
        Matcher m = doublePattrern.matcher(input);
        if(m.matches()){
            this.valueType = DOUBLE;
            return true;
        }
        return false;
    }

    // checks is String input represents an integer (valid input of boolean)
    private boolean checkIsInputInteger(String input){
        Pattern integerPattrern = Pattern.compile("^[+-]?\\d+$");
        Matcher m = integerPattrern.matcher(input);
        if(m.matches()){
            this.valueType = INTEGER;
            return true;
        }
        return false;
    }

    // switches a string that represents integer into a boolean
    private void switchIntegerToBoolean(String input){
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        int boolIntValue = Integer.parseInt(cleanedInput);
        if(boolIntValue != 0 ){
            this.value = true;
        }
        else{
            this.value = false;
        }
    }

    // switches a string that represents double into a boolean
    private void switchDoubleToBoolean(String input){
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        double boolDoubleValue = Double.parseDouble(cleanedInput);
        if(boolDoubleValue != 0 ){
            this.value = true;
        }
        else {
            this.value = false;
        }
    }

    // switches a string that represents boolean into a boolean
    private void switchBooleanToBoolean(String input){
        if(input.equals(TRUE)){
            this.value = true;
        }
        else if(input.equals(FALSE)){
            this.value = false;
        }
    }

    // this function updates the value of boolean variable
    private void updateValue(String input){
        if(isValidInput(input)){
            switch(this.valueType){
                case INTEGER:
                    switchIntegerToBoolean(input);
                    break;
                case DOUBLE:
                    switchDoubleToBoolean(input);
                    break;
                default:
                    switchBooleanToBoolean(input);
                    break;
            }
        }
        else{
            throw new InvalidBooleanException();
        }
    }
}
