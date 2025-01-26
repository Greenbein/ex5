package managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharManager implements ManagerInterface<Character>{
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[^\\\\'\",]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public Character extractValue(String input) {
        return input.charAt(0);
    }
}
