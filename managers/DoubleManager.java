package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleManager implements ManagerInterface<Double> {
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

    @Override
    public Double extractValue(String input) {
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        return Double.parseDouble(cleanedInput);
    }
}
