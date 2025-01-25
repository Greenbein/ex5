package variables.string_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSJava extends Variable {
    private String value;
    public StringSJava(String name, int layer, boolean isFinal, String value) {
        super(name, layer, isFinal, true);
        initializeValue(value);
    }
    public StringSJava(String name, int layer, boolean isFinal) {
        super(name, layer, isFinal, false);
    }

//    public String toString(){
//        return this.value;
//    }

    /**
     * checks is the input of the variable valid
     * @param input the input of the variable
     * @return
     */
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^.*[\\\\'\",].*$");
        Matcher m = p.matcher(input);
        return !m.matches();

    }
    @Override
    public void initializeValue(String input) {
         if(this.isValidInput(input)){
             this.value = input;
         }
         else{
             throw new InvalidStringInputException(this.getName());
         }
    }
    @Override
    public void setValue(String input) {
        if(this.isValidInput(input)){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInput(input)){
            this.value = input;
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }
}
