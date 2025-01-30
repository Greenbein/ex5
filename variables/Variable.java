package variables;

import databases.VariableDataBase;
import variables.exceptions.*;
import valid_name.*;
import variables.variable_managers.*;

/**
 * This class implements java variable
 */
public class Variable {
    private String name;
    private final int layer;
    private final boolean isFinal;
    private boolean isInitialized;
    private final VariableType type;
    private Object value;
    private VariableDataBase db;

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
                       String value, VariableDataBase db) {
        updateName(name);
        // the variable can't be final without being
        // initialized (not supposed to happened)
        if(!isInitialized && isFinal) {
            throw new InvalidFinalVariableInitializationException();
        }
        this.layer = layer;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.type = type;
        this.db = db;
        initializeValue(value);
    }

    /**
     * Constructor for uninitialized variable
     * @param name - variable's name
     * @param layer - variable's layer (for scope)
     * @param isFinal - isFinal must be equal to false
     * @param type - variable's type
     */
    public Variable(String name,
                    int layer,
                    boolean isFinal,
                    VariableType type,
                    VariableDataBase db) {
        updateName(name);
        this.layer = layer;
        if(isFinal) {
            throw new InvalidFinalVariableInitializationException();
        }
        this.isFinal = false;
        this.isInitialized = false;
        this.type = type;
        this.db = db;
        this.value = null;
    }



    /**
     * this function sets value variable
     * the value of the certain variable
     * @param input the value we need to set
     */
    public void setValue(String input){
        if(ValidName.isValidVarNameInput(input)) {
            Variable other = this.db.findVarByNameOnly(input,this.layer);
            if(other != null) {
                if(this.isFinal&&this.isInitialized){
                    throw new FinalVariableUpdatingException(this.name);
                }
                if(!this.type.equals(other.type)){
                    throw new UpdateValueTypeErrorException
                            (this.name,this.type.toString(),other.type.toString());
                }
                this.setValue(other.getValue());
                return;
            }
            else{
                throw new UnreachableVariableException(input);
            }
        }
        IntegerManager integerManager = new IntegerManager();
        DoubleManager doubleManager = new DoubleManager();
        BooleanManager booleanManager = new BooleanManager
                (integerManager,doubleManager,this);
        CharManager charManager = new CharManager();
        StringManager stringManager =  new StringManager();
        switch(type){
            case INT:
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
        if(ValidName.isValidVarNameInput(input)){
            Variable var = this.db.findVarByNameAndType(input,this.layer,this.type);
            if(var != null){
                this.value = var.getValue();
                this.db.addVariable(this);
                return;
            }
            else{
                throw new InvalidVariableAssignmentException(input);
            }
        }
        IntegerManager integerManager = new IntegerManager();
        DoubleManager doubleManager = new DoubleManager();
        BooleanManager booleanManager =
                new BooleanManager(integerManager,doubleManager,this);
        CharManager charManager = new CharManager();
        StringManager stringManager =  new StringManager();
        switch(this.type){
            case INT:
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

    // this function checks and apply a name to a variable if it is valid
    // if not throwing relevant exception
    private void updateName(String name){
        if(ValidName.isValidVariableName(name)){
            this.name = name;
        }
    }

    //-------------------------getters-----------------------------//
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
     * get variable type
     * @return enum VariableType
     */
    public VariableType getValueType() {
        return this.type;
    }

    //-------------------------setters-----------------------------//
    /**
     * set variable's value
     * @param value - new value
     */
    public void setValue(Object value) {
        this.value = value;
        if (!this.isInitialized && value!=null){
            this.isInitialized=true;
        }
    }

    /**
     * ToString method
     * @return String
     */
    public String toString() {
        if (this.isFinal) {
            return "final " + this.type.toString()
                    + " " + this.name + " = " + this.value.toString();
        } else {
            if (this.isInitialized) {
                return this.type.toString()
                        + " " + this.name + " = " + this.value.toString();
            } else {
                return this.type.toString() + " " + this.name;
            }
        }
    }
}
