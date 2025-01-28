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
        Pattern pDouble = Pattern.compile("^[+-]?(\\d*.\\d+|\\d+.\\d*)$");
        Pattern pInteger = Pattern.compile("^[+-]?\\d+$");
        Matcher mInteger = pInteger.matcher(input);
        Matcher mDouble = pDouble.matcher(input);
        if(mInteger.matches()|| mDouble.matches()) {
            return true;
        }
        return false;
//        Pattern inputWithOperatorInt =
//                Pattern.compile("^[+-]?\\d+\\s*[+-/*]\\s*[+-]?\\d+]$");
//        Matcher matcherInputWithOperator = inputWithOperatorInt.matcher(input);
//        if(matcherInputWithOperator.matches()){
//            throw new inputWithOperatorException(input);
//        }
//        Pattern inputWithOperatorDouble =
//                Pattern.compile("^[+-]?(\\d*.\\d+\\s*[+-/*]\\s*\\d*.\\d+|"+
//                                      "\\d*.\\d+\\s*[+-/*]\\s*\\d+.\\d*|"+
//                                      "\\d+.\\d*\\s*[+-/*]\\s*\\d*.\\d+|"+
//                                      "\\d+.\\d*\\s*[+-/*]\\s*\\d+.\\d*)$");
//        matcherInputWithOperator = inputWithOperatorDouble.matcher(input);
//        if(matcherInputWithOperator.matches()){
//            throw new inputWithOperatorException(input);
//        }
//        return false;
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
