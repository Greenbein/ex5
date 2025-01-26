package variables.exceptions;

/**
 * exception for the case we try to create final variable without a value
 */
public class InvalidFinalVariableInitializationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid initializing: final variable must be initialized";

    /**
     * default constructor exception
     */
    public InvalidFinalVariableInitializationException() {
        super(DEFAULT_MESSAGE);
    }
}
