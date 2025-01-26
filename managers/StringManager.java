package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringManager implements ManagerInterface<String> {
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^.*[\\\\'\",].*$");
        Matcher m = p.matcher(input);
        return !m.matches();
    }

    @Override
    public String extractValue(String input) {
        return input;
    }
}
