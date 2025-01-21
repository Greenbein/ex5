package variables;

import variables.basic_exceptions.InvalidFormatName;
import variables.basic_exceptions.NameStartsWithDoubleUnderscoreException;
import variables.basic_exceptions.NameStartsWithNumberException;

public abstract class Variable {
    private String name;
    private int layer;
    private boolean isFinal;
    private boolean isInitialized;
    protected Variable(String name,
                       int layer,
                       boolean isFinal,
                       boolean isInitialized) {
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
    public abstract void updateValue(String input);

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
    public boolean isFinal() {
        return this.isFinal;
    }
    public boolean isInitialized() {
        return this.isInitialized;
    }
    public String getName() {
        return this.name;
    }
    public int getLayer() {
        return this.layer;
    }


}
