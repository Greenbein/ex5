package variables.exceptions;

/**
 * exception for the case we try to create final variable without a value
 */
public class InvalidFinalVariableIntialization extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid initializing: if a variable is final must be initialized";

    /**
     * default constructor exception
     */
    public InvalidFinalVariableIntialization() {
        super(DEFAULT_MESSAGE);
    }
}
