package variables.variable_managers;

import valid_name.ValidName;
import variables.Variable;
import variables.exceptions.*;
import variables.exceptions.input_exceptions.*;

/**
 * Interface for managers
 * @param <T> - variable's type
 */
public interface ManagerInterface<T> {
    /**
     * Check if the input is valid for some variable type
     * @param input - input for variable
     * @return true or false
     */
    boolean isValidInput(String input);

    /**
     * Extract value for some variable type from its input string
     * @param input - string contains variable's value
     * @return - value of type T (generics)
     */
    T extractValue(String input);

    /**
     * Throw invalid input exception according to variable's type
     * @param variable - variable
     */
    default void throwInvalidInputException(Variable variable){
        switch (variable.getValueType()){
            case INT:
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
    //-----------------default methods------------------------

    // this function apply the value in the input into the variable
    // if the input is valid else throw exception
    default void initializeValue(String input, Variable variable){
        if(isValidInput(input)){
            variable.setValue(this.extractValue(input));
        }
        else{
            throwInvalidInputException(variable);
        }
    }

    // this function also apply the value in the input into the variable
    // with the exception that if the variable is final it also
    // would throw an exception (can set new value to final variable)
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
