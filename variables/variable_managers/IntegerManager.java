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
        System.out.println("MY INPUT IS: "+input);
        Pattern p = Pattern.compile("^[+-]?\\d+$");
        Matcher m = p.matcher(input);
        return m.matches();
//        Pattern inputWithOperator =
//                Pattern.compile("^[+-]?\\d+\\s*[+-/*]\\s*[+-]?\\d+]$");
//        Matcher matcherInputWithOperator = inputWithOperator.matcher(input);
//        if(matcherInputWithOperator.matches()){
//            throw new inputWithOperatorException(input);
//        }
//        throw new InvalidIntegerException(input);
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
