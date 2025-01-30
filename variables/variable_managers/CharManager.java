package variables.variable_managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for character variables,
 * initialize and update them
 */
public class CharManager
        implements ManagerInterface<Character>{
    private static final String DEFAULT_INPUT = "default value";
    private static final String CORRECTNESS_VERIFIER_REGEX
            = "^'[^\"',\\\\]'$";
    private static final char DEFAULT_CHAR = '.';
    private static final int FIRST = 1;
    /**
     * Constructor for CHarManager
     */
    public CharManager() {}
    /**
     * Check if the input is correct
     * @param input - string for character variable value
     * @return - true or false
     */
    @Override
    public boolean isValidInput(String input) {
        if(input.equals(DEFAULT_INPUT)){
            return true;
        }
        Pattern patternChar =
                Pattern.compile(CORRECTNESS_VERIFIER_REGEX);
        Matcher matcherChar = patternChar.matcher(input);
        return matcherChar.matches();
    }

    /**
     * Extract value for Character variable
     * @param input - input for character
     * @return - char value
     */
    @Override
    public Character extractValue(String input) {
        if(input.equals(DEFAULT_INPUT)){
            return DEFAULT_CHAR;
        }
        return input.charAt(FIRST);
    }
}
