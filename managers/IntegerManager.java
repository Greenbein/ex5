package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for integer variables,
 * initialize and update them
 */
public class IntegerManager implements ManagerInterface<Integer> {
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
        Pattern p = Pattern.compile("^[+-]?\\d+$");
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
        if (input.equals("0")) {
            return 0;
        }
        String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
        return Integer.parseInt(cleanedInput);
    }
}
