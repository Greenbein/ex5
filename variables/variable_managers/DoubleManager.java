package variables.variable_managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for double variables,
 * initialize and update them
 */
public class DoubleManager implements ManagerInterface<Double> {
    /**
     * DoubleManager constructor
     */
    public DoubleManager() {}

    /**
     * Check if the input is correct
     * @param input - string for double variable value
     * @return - true or false
     */
    @Override
    public boolean isValidInput(String input) {
        if(input.equals("default value")){
            return true;
        }
        Pattern pDouble = Pattern.compile("^[+-]?(\\d*.\\d+|\\d+.\\d*)$");
        Pattern pInteger = Pattern.compile("^[+-]?\\d+$");
        Matcher mInteger = pInteger.matcher(input);
        Matcher mDouble = pDouble.matcher(input);
        if(mInteger.matches()|| mDouble.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Extract value for Double variable
     * @param input - input for double
     * @return - double value
     */
    @Override
    public Double extractValue(String input) {
        if(input.equals("default value")){
            return 0.0;
        }
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        return Double.parseDouble(cleanedInput);
    }
}
