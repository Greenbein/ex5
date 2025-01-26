package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class allows us to process inputs for character variables,
 * initialize and update them
 */
public class CharManager implements ManagerInterface<Character>{
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
        Pattern p = Pattern.compile("^[^\\\\'\",]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Extract value for Character variable
     * @param input - input for character
     * @return - char value
     */
    @Override
    public Character extractValue(String input) {
        return input.charAt(0);
    }
}
