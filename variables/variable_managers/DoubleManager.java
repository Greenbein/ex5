package variables.variable_managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for double variables,
 * initialize and update them
 */
public class DoubleManager implements ManagerInterface<Double> {
    private static final String DEFAULT_INPUT = "default value";
    private static final double DEFAULT_VALUE = 0.0;
    private static final String
            CORRECTNESS_VERIFIER_REGEX_1
            = "^[+-]?(\\d*.\\d+|\\d+.\\d*)$";
    private static final String
            CORRECTNESS_VERIFIER_REGEX_2
            = "^[+-]?\\d+$";
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
        if(input.equals(DEFAULT_INPUT)){
            return true;
        }
        Pattern pDouble = Pattern.compile(CORRECTNESS_VERIFIER_REGEX_1);
        Pattern pInteger = Pattern.compile(CORRECTNESS_VERIFIER_REGEX_2);
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
        if(input.equals(DEFAULT_INPUT)){
            return DEFAULT_VALUE;
        }
        String cleanedInput = input.replaceFirst
                ("^[+]", "").
                replaceFirst("^0+", "");
        return Double.parseDouble(cleanedInput);
    }
}
