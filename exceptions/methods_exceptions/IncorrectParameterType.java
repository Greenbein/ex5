package exceptions.methods_exceptions;

import variables.VariableType;

/**
 * Exception in the case of incorrect parameter type
 */
public class IncorrectParameterType extends RuntimeException {
    private static final String MESSAGE = "%d's parameter of method '%s' is %s, but %s was received.";

    /**
     * Exception's constructor
     * @param parameterIndex - the index of wrong parameter
     * @param methodName - the name o function
     * @param expectedType - expected i's parameter type
     * @param gottenType - received type
     */
    public IncorrectParameterType(int parameterIndex, String methodName, VariableType expectedType, VariableType gottenType) {
        super(String.format(MESSAGE, parameterIndex, methodName, expectedType, gottenType));
    }
}
