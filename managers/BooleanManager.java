package managers;

import variables.Variable;
import variables.exceptions.InvalidBooleanException;
import variables.exceptions.InvalidSetFinalVariableException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanManager implements ManagerInterface<Boolean> {
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private final IntegerManager integerManager;
    private final DoubleManager doubleManager;
    private final Variable variable;
    private String valueTypeForBoolean;

    public BooleanManager(IntegerManager integerManager, DoubleManager doubleManager, Variable variable) {
        this.integerManager = integerManager;
        this.doubleManager = doubleManager;
        this.variable = variable;
    }
    // checks is String input represents a boolean
    private boolean checkIsInputBoolean(String input){
        Pattern truePattern = Pattern.compile("^true$");
        Matcher m = truePattern.matcher(input);
        if(m.matches()){
            this.setValueTypeForBoolean(TRUE);
            return true;
        }
        Pattern falsePattern = Pattern.compile("^false$");
        m = falsePattern.matcher(input);
        if(m.matches()){
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

    @Override
    public Boolean extractValue(String input) {
        if(this.isValidInput(input)){
            return switch (this.getValueTypeForBoolean()) {
                case INTEGER -> switchIntegerToBoolean(input);
                case DOUBLE -> switchDoubleToBoolean(input);
                default -> switchBooleanToBoolean(input);
            };
        }
        else{
            throw new InvalidBooleanException(this.variable.getName());
        }
    }

    private void setValueTypeForBoolean(String valueTypeForBoolean) {
        this.valueTypeForBoolean = valueTypeForBoolean;
    }

    private String getValueTypeForBoolean(){
        return this.valueTypeForBoolean;
    }
}
