package variables.variable_managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The class allows us to process inputs for string variables,
 * initialize and update them
 */
public class StringManager implements ManagerInterface<String> {
    private static final String
            DEFAULT_VALUE = "default value";
    private static final String
            DEFAULT_STRING_VALUE = "default string";
    private static final String
            CORRECTNESS_VERIFIER_REGEX = "^\"[^\"',\\\\]*\"$";
    /**
     * constructor for StringManager
     */
    public StringManager() {}

    /**
     * Check if the input is correct
     * @param input - string for String variable value
     * @return - true or false
     */
    @Override
    public boolean isValidInput(String input) {
        if(input.equals(DEFAULT_VALUE)){
            return true;
        }
        Pattern patternString =
                Pattern.compile(CORRECTNESS_VERIFIER_REGEX);
        Matcher matcherString = patternString.matcher(input);
        return matcherString.matches();
    }

    /**
     * Extract value for String variable
     * @param input - input for string
     * @return - String value
     */
    @Override
    public String extractValue(String input) {
        if(input.equals(DEFAULT_VALUE)){
            return DEFAULT_STRING_VALUE;
        }
        return input.substring(1, input.length() - 1);
    }
}
