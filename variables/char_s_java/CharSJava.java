package variables.char_s_java;

import variables.Variable;
import variables.char_s_java.exceptions.InvalidCharException;
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
        if(this.isValidInput(value)){
            this.updateValue(value);
        }
        else{
            throw new InvalidCharException(name);
        }
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


    public String toString(){
        return String.valueOf(this.value);
    }

    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^[^\\\\'\",]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public void updateValue(String input) {
        this.value = input.charAt(0);
    }

}
