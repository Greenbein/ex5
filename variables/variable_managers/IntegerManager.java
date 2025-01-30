package variables.variable_managers;

import valid_name.ValidName;
import variables.exceptions.input_exceptions.InvalidIntegerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for integer variables,
 * initialize and update them
 */
public class IntegerManager implements ManagerInterface<Integer> {
    private static final String DEFAULT_INPUT = "default value";
    private static final String
            CORRECTNESS_VERIFIER_REGEX = "^[+-]?\\d+$";
    private static final String ZERO_STRING = "0";
    private static final int DEFAULT_VALUE = 0;
    /**
     * IntegerManager constructor
     */
    public IntegerManager() {}
    /**
     * Check if the input is correct
     * @param input - string for Integer variable value
     * @return - true or false
     */
    @Override
    public boolean isValidInput(String input) {
        if(input.equals(DEFAULT_INPUT)){
            return true;
        }
        Pattern p = Pattern.compile(CORRECTNESS_VERIFIER_REGEX);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Extract value for Integer variable
     * @param input - input for integer
     * @return - integer value
     */
    @Override
    public Integer extractValue(String input) {
        if(input.equals(DEFAULT_INPUT)){
            return DEFAULT_VALUE;
        }
        if (input.equals(ZERO_STRING)) {
            return DEFAULT_VALUE;
        }
        String cleanedInput = input.replaceFirst("^[+]", "").
                replaceFirst("^0+", "");
        return Integer.parseInt(cleanedInput);
    }
}
