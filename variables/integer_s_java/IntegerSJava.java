package variables.integer_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.integer_s_java.exceptions.InvalidIntegerInputException;
import variables.string_s_java.exceptions.InvalidStringInputException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for integer variable
 */
public class IntegerSJava extends Variable {
    private int value;

    /**
     * Integer constructor with initialization
     * @param name - integer's name
     * @param layer - its layer (scope)
     * @param isFinal - final or not
     * @param value - integer's value
     */
     public IntegerSJava(String name, int layer, boolean isFinal, String value){
        super(name, layer, isFinal, true);
         initializeValue(value);
    }

    /**
     * Integer constructor without initialization
     * @param name - integer's name
     * @param layer - its layer (scope)
     * @param isFinal - final or not
     */
    public IntegerSJava(String name, int layer, boolean isFinal){
        super(name, layer, isFinal, false);
    }

    /**
     * check whether the input is valid for integerSJava variable
     * @param input the input of the variable
     * @return true or false
     */
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[+-]?\\d+$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Initialize integerSJava
     * @param input the value we need to implement in the initializes variable
     */
    @Override
    public void initializeValue(String input) {
        if(this.isValidInput(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    /**
     * Set new value to integerSJava variable
     * @param input the value we need to set
     */
    @Override
    public void setValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInput(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidIntegerInputException(this.getName());
        }
    }

    /**
     * toString method for printing integerSJava variable
     * @return string copy of variable's name
     */
    public String toString(){
         return String.valueOf(this.value);
    }
}
