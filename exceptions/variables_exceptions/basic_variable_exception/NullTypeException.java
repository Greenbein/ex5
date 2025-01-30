package exceptions.variables_exceptions.basic_variable_exception;

/**
 * We use it when we want to get VariableType from null String
 */
public class NullTypeException extends RuntimeException {
    private static final String MESSAGE = "The provided type is null";
    /**
     * NullTypeException constructor
     */
    public NullTypeException() {
        super(MESSAGE);
    }
}
