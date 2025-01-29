package variables.variable_managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The class allows us to process inputs for string variables,
 * initialize and update them
 */
public class StringManager implements ManagerInterface<String> {
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
        if(input.equals("default value")){
            return true;
        }
        Pattern patternString = Pattern.compile("^\"[^\"',\\\\]*\"$");
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
        if(input.equals("default value")){
            return "default string";
        }
        return input.substring(1, input.length() - 1);
    }
}
