package variables;

import variables.basic_exceptions.InvalidFinalVariableIntialization;
import variables.basic_exceptions.InvalidFormatName;
import variables.basic_exceptions.NameStartsWithDoubleUnderscoreException;
import variables.basic_exceptions.NameStartsWithNumberException;

/**
 * This class implements java variable
 */
public abstract class Variable {
    private String name;
    private int layer;
    private boolean isFinal;
    private boolean isInitialized;

    /**
     * default constructor of variable
     * @param name name of variable
     * @param layer layer of variable
     * @param isFinal is variable final
     * @param isInitialized is variable initialized
     */
    public Variable(String name,
                       int layer,
                       boolean isFinal,
                       boolean isInitialized) {
        if(!isInitialized && isFinal) {
            throw new InvalidFinalVariableIntialization();
        }
        if(isValidName(name)) {
            this.name = name;
        }
        this.layer = layer;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }

    /**
     * this function checks is the input we got in the
     * variable is valid if not throw relevant exception
     * @param input the input of the variable
     * @return is the input valid or not
     */
    public abstract boolean isValidInput(String input);

    /**
     * this function sets
     * the value of the certain variable
     * @param input the value we need to set
     */
    public abstract void setValue(String input);

    /**
     * this function initializes
     * the value of the certain variable
     * @param input the value we need to implement in the initializes variable
     */
    public abstract void initializeValue(String input);

    // this function checks is a name of a variable is valid
    // if not throwing relevant exception
    private boolean isValidName(String name){
        if(name.equals("_")){
            // exception name is only _
            throw new NameStartsWithNumberException();
        }
        if(name.matches("^__.*")){
            // exception starts with double __
            throw new NameStartsWithDoubleUnderscoreException();
        }
        if(name.matches("^\\d{1}.*")){
            // exception starts with a number
            throw new NameStartsWithNumberException();
        }
       if(!name.matches("^\\w+$")){
           // exception illegal format using invalid format
           throw new InvalidFormatName();
       }
       return true;
    }

    // getters

    // return is a variable is final
    public boolean isFinal() {
        return this.isFinal;
    }

    // return is a variable initialized
    public boolean isInitialized() {
        return this.isInitialized;
    }

    // returns the name of the variable
    public String getName() {
        return this.name;
    }

    // returns the layer of the object
    public int getLayer() {
        return this.layer;
    }


}
