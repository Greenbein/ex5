package managers;

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
     * Extract value for Double variable
     * @param input - input for double
     * @return - double value
     */
    @Override
    public Double extractValue(String input) {
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        return Double.parseDouble(cleanedInput);
    }
}
