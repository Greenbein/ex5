package variables.char_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.char_s_java.exceptions.InvalidCharException;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CharSJava class for char variable in a code
 */
public class CharSJava extends Variable {
    private char value;

    /**
     * First CharSJava constructor. We use it when the char is initialized
     * @param name - char's name
     * @param layer - its layer in the code (scope)
     * @param isFinal - char is final or not
     * @param value - char's value
     */
    public CharSJava(String name, int layer, boolean isFinal, String value) {
        super(name, layer, isFinal, true);
        initializeValue(value);
    }

    /**
     * Second CharSJava constructor. We use it when the variable is not initialized
     * @param name - char's name
     * @param layer - its layer in the code (scope)
     * @param isFinal - char's value
     */
    public CharSJava(String name, int layer, boolean isFinal) {
        super(name, layer, isFinal, false);
    }


//    public String toString(){
//        return String.valueOf(this.value);
//    }

    /**
     * this function checks is the input for the variable is valid
     * @param input the input of the variable
     * @return is the input valid or not
     */
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[^\\\\'\",]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * this function sets the value of variable char
     * @param input the value we need to set
     */
    @Override
    public void setValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInput(input)){
            this.value = input.charAt(0);
        }
        else{
            throw new InvalidCharException(this.getName());
        }
    }

    /**
     * this function adding the value of variable char into the variable in initialization
     * @param input the value we need to implement in the initializes variable
     */
    @Override
    public void initializeValue(String input) {
        if(isValidInput(input)){
            this.value = input.charAt(0);
        }
        else{
            throw new InvalidCharException(this.getName());
        }
    }
}
