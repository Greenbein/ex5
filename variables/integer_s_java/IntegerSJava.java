package variables.integer_s_java;

import variables.Variable;
import variables.integer_s_java.exceptions.InvalidIntegerInputException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class IntegerSJava extends Variable {
    private int value;
     public IntegerSJava(String name, int layer, boolean isFinal, String value){
        super(name, layer, isFinal, true);
        if(this.isValidInput(value)){
            this.updateValue(value);
        }
        else{
            throw new InvalidIntegerInputException(name);
        }
    }
    public IntegerSJava(String name, int layer, boolean isFinal){
        super(name, layer, isFinal, false);
    }

    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[+-]?\\d+$");
         Matcher m = p.matcher(input);
         return m.matches();
    }

    @Override
    public void updateValue(String input) {
        String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
        this.value = Integer.parseInt(cleanedInput);
    }

    public String toString(){
         return String.valueOf(this.value);
    }
}
