package variables.double_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.double_s_java.exceptions.InvalidDoubleException;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements double variable at java
 */
public class DoubleSJava extends Variable {
    private int value;

    /**
     * initialized constructor of variable double
     * @param name name of the double variable
     * @param layer layer of the double variable
     * @param isFinal is the double variable final or not
     * @param isInitialized is the double variable initialized or not
     * @param value the value of the double variable
     */
    public DoubleSJava(String name, int layer, boolean isFinal, boolean isInitialized, String value) {
        super(name, layer, isFinal, isInitialized);
        initializeValue(value);
    }

    /**
     * not initialized constructor of variable double
     * @param name name of the double variable
     * @param layer layer of the double variable
     * @param isFinal is the double variable final or not
     * @param isInitialized is the double variable initialized or not
     */
    public DoubleSJava(String name, int layer, boolean isFinal, boolean isInitialized) {
        super(name, layer, isFinal, isInitialized);
    }

    /**
     * this function checks is the given input for the variable is valid
     * @param input the input of the variable
     * @return is the input valid or not
     */
    @Override
    public boolean isValidInput(String input) {
        Pattern pDouble = Pattern.compile("^[+-]?(\\d*.\\d+|^[+-]?\\d+.\\d*)$");
        Pattern pInteger = Pattern.compile("^[+-]?\\d+$");
        Matcher mInteger = pInteger.matcher(input);
        if(mInteger.matches()) {
            return true;
        }
        Matcher mDouble = pDouble.matcher(input);
        return mDouble.matches();
    }

    /**
     * this function sets the value of double variable
     * @param input the value we need to set
     */
    @Override
    public void setValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInput(input)){
            String cleanedInput = input.replaceFirst
                    ("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    /**
     * this function initializes the value of double variable
     * @param input the value we need to implement in the initializes variable
     */
    @Override
    public void initializeValue(String input) {
        if(isValidInput(input)){
            String cleanedInput = input.replaceFirst
                    ("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }
}
