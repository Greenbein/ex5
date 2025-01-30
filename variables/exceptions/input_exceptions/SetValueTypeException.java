package variables.exceptions.input_exceptions;

import variables.VariableType;

/**
 * Type error in the vase we try to set to variable a mew value with incorrect type
 */
public class SetValueTypeException extends RuntimeException {
    private static final String MESSAGE = "The variable 's%' is 's%', so it can't get a value of type 's%'";
    /**
     * SetValueTypeException constructor
     */
    public SetValueTypeException(String varName, VariableType expected, VariableType received) {
        super(String.format(MESSAGE, varName, expected.toString(),received.toString()));
    }
}
