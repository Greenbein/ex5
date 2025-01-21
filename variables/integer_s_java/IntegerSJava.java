package variables.integer_s_java;

import variables.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerSJava extends Variable {
    private int value;
     public IntegerSJava(String name, int layer, boolean isFinal, boolean isInitialized, String value) {
        super(name, layer, isFinal, isInitialized);
        if(this.isValidInput(value)){
            this.updateValue(value);
        }
    }

    @Override
    public boolean isValidInput(String input) {
         Pattern p = Pattern.compile("^[+-]?/d+$");
         Matcher m = p.matcher(input);
         return m.matches();
    }

    @Override
    public void updateValue(String input) {
        String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
        this.value = Integer.parseInt(cleanedInput);
    }

    pu
}
