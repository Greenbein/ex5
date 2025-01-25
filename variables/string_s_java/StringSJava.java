package variables.string_s_java;

import variables.Variable;
import variables.basic_exceptions.InvalidSetFinalVariableException;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implement the variable string in java
 */
public class StringSJava extends Variable {
    private String value;

    /**
     * initialized constructor of String
     * @param name name of the string variable
     * @param layer layer of the string variable
     * @param isFinal is the String variable final
     * @param value the value of the string variable
     */
    public StringSJava(String name, int layer, boolean isFinal, String value) {
        super(name, layer, isFinal, true);
        initializeValue(value);
    }

    /**
     * not initialized constructor of String
     * @param name name of the string variable
     * @param layer layer of the string variable
     * @param isFinal is the String variable final
     */
    public StringSJava(String name, int layer, boolean isFinal) {
        super(name, layer, isFinal, false);
    }

//    public String toString(){
//        return this.value;
//    }

    /**
     * checks is the input of the variable valid
     * @param input the input of the variable
     * @return is the input is valid
     */
    @Override
    public boolean isValidInput(String input) {
        Pattern p = Pattern.compile("^.*[\\\\'\",].*$");
        Matcher m = p.matcher(input);
        return !m.matches();

    }

    /**
     * this function initializes String variable
     * @param input the value we need to implement in the initializes variable
     */
    @Override
    public void initializeValue(String input) {
         if(this.isValidInput(input)){
             this.value = input;
         }
         else{
             throw new InvalidStringInputException(this.getName());
         }
    }

    /**
     * this function set value for a variable of type
     * String
     * @param input the value we need to set
     */
    @Override
    public void setValue(String input) {
        if(this.isFinal()){
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
