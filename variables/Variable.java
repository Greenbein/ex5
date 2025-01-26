package variables;

import managers.*;
import variables.exceptions.*;

/**
 * This class implements java variable
 */
public class Variable {
    private String name;
    private final int layer;
    private final boolean isFinal;
    private final boolean isInitialized;
    private final VariableType type;
    private Object value;

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
                       boolean isInitialized,
                       VariableType type,
                       String value) {
        updateName(name);
        // the variable can't be final without being initialized
        if(!isInitialized && isFinal) {
            throw new InvalidFinalVariableIntialization();
        }
        // uninitialized variable does not have any value in it
        if (!isInitialized && value != null) {
            throw new AssignNonInitializedVariableError(name);
        }
        this.layer = layer;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.type = type;
        initializeValue(value);
    }

    /**
     * Constructor for uninitialized variable
     * @param name - variable's name
     * @param layer - variable's layer (for scope)
     * @param isFinal - isFinal must be equal to false
     * @param type - variable's type
     */
    public Variable(String name,int layer,boolean isFinal,VariableType type){
        updateName(name);
        this.layer = layer;
        if(isFinal) {
            throw new InvalidFinalVariableIntialization();
        }
        this.isFinal = false;
        this.isInitialized = false;
        this.type = type;
        this.value = null;
    }

    /**
     * this function sets
     * the value of the certain variable
     * @param input the value we need to set
     */
    public void setValue(String input){
        IntegerManager integerManager = new IntegerManager();
        DoubleManager doubleManager = new DoubleManager();
        BooleanManager booleanManager = new BooleanManager(integerManager,doubleManager,this);
        CharManager charManager = new CharManager();
        StringManager stringManager =  new StringManager();
        switch(type){
            case INTEGER:
                integerManager.setValue(input,this);
                break;
            case DOUBLE:
                doubleManager.setValue(input,this);
                break;
            case BOOLEAN:
                booleanManager.setValue(input,this);
                break;
            case STRING:
                stringManager.setValue(input,this);
                break;
            default:
                charManager.setValue(input,this);
                break;
        }
    }

    /**
     * this function initializes
     * the value of the certain variable
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValue(String input){
        IntegerManager integerManager = new IntegerManager();
        DoubleManager doubleManager = new DoubleManager();
        BooleanManager booleanManager = new BooleanManager(integerManager,doubleManager,this);
        CharManager charManager = new CharManager();
        StringManager stringManager =  new StringManager();
        switch(this.type){
            case INTEGER:
                integerManager.initializeValue(input,this);
                break;
            case DOUBLE:
                doubleManager.initializeValue(input,this);
                break;
            case BOOLEAN:
                booleanManager.initializeValue(input,this);
                break;
            case STRING:
                stringManager.initializeValue(input,this);
                break;
            default:
                charManager.initializeValue(input,this);
                break;
        }
    }

    // this function checks is a name of a variable is valid
    // if not throwing relevant exception
    private void updateName(String name){
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
       else{
           this.name = name;
       }
    }
    /**
     * check if the variable is final or not
     * @return isFinal status
     */
    public boolean isFinal() {
        return this.isFinal;
    }

    /**
     * check if the value is initialized
     * @return initialization status
     */
    public boolean isInitialized() {
        return this.isInitialized;
    }

    /**
     * getter for variable's name
     * @return variable's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for layer
     * @return var's layer
     */
    public int getLayer() {
        return this.layer;
    }

    /**
     * get variable's value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * set variable's value
     * @param value - new value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * get variable type
     * @return enum VariableType
     */
    public VariableType getValueType() {
        return this.type;
    }
}
