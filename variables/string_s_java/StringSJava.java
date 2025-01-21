package variables.string_s_java;

import variables.Variable;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSJava extends Variable {
    private String value;
    public StringSJava(String name, int layer, boolean isFinal, String value) {
        super(name, layer, isFinal, true);
        if(this.isValidInput(value)){
            this.updateValue(value);
        }
        else{
            throw new InvalidStringInputException(name);
        }
    }

    public String toString(){
        return this.value;
    }

    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[^\\\\'\",]*$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public void updateValue(String input) {
        this.value = input;
    }
}
