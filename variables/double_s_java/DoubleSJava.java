package variables.double_s_java;

import variables.Variable;
import variables.double_s_java.exceptions.InvalidDoubleException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleSJava extends Variable {
    private int value;

    public DoubleSJava(String name, int layer, boolean isFinal, boolean isInitialized, String value) {
        super(name, layer, isFinal, isInitialized);
        if(this.isValidInput(value)){
            this.updateValue(value);
        }
        else{
            throw new InvalidDoubleException();
        }
    }

    public DoubleSJava(String name, int layer, boolean isFinal, boolean isInitialized) {
        super(name, layer, isFinal, false);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[+-]?(\\d*.\\d+|^[+-]?\\d+.\\d*)$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public void updateValue(String input) {
        String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
        this.value = Integer.parseInt(cleanedInput);
    }

}
