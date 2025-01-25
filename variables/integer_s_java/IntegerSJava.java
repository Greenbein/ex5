package variables.integer_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.integer_s_java.exceptions.InvalidIntegerInputException;
import variables.string_s_java.exceptions.InvalidStringInputException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerSJava extends Variable {
    private int value;
     public IntegerSJava(String name, int layer, boolean isFinal, String value){
        super(name, layer, isFinal, true);
         initializeValue(value);
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
    public void initializeValue(String input) {
        if(this.isValidInput(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    @Override
    public void setValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInput(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidIntegerInputException(this.getName());
        }
    }

    public String toString(){
         return String.valueOf(this.value);
    }
}
