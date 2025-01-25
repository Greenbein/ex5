package variables.basic_exceptions;

/**
 * exception to trying to set a new value for a final variable
 */
public class InvalidSetFinalVariableException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid set: if a variable is final you can't set a new value";

    /**
     * default contractor for exception
     */
    public InvalidSetFinalVariableException() {
        super(DEFAULT_MESSAGE);
    }
}
