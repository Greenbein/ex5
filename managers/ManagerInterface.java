package managers;

import variables.Variable;
import variables.exceptions.*;

public interface ManagerInterface<T> {
    boolean isValidInput(String input);
    T extractValue(String input);
    default void throwInvalidInputException(Variable variable){
        switch (variable.getValueType()){
            case INTEGER:
                throw new InvalidIntegerException(variable.getName());
            case DOUBLE:
                throw new InvalidDoubleException(variable.getName());
            case STRING:
                throw new InvalidStringException(variable.getName());
            case BOOLEAN:
                throw new InvalidBooleanException(variable.getName());
            default:
                throw new InvalidCharException(variable.getName());

        }
    }
    default void initializeValue(String input, Variable variable){
        if(isValidInput(input)){
            variable.setValue(this.extractValue(input));
        }
        else{
            throwInvalidInputException(variable);
        }
    }
    default void setValue(String input, Variable variable){
        if(variable.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(this.isValidInput(input)){
            variable.setValue(this.extractValue(input));
        }
        else{
            throwInvalidInputException(variable);
        }
    }
}
