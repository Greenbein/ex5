package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerManager implements ManagerInterface<Integer> {
    /**
     * IntegerManager constructor
     */
    public IntegerManager() {}
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[+-]?\\d+$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public Integer extractValue(String input) {
        if (input.equals("0")) {
            return 0;
        }
        String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
        return Integer.parseInt(cleanedInput);
    }
}
