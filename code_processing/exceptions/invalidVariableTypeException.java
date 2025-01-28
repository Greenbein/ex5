package code_processing.exceptions;

/**
 * this exception addresses the case we had invalid variable type
 */
public class invalidVariableTypeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "invalid variable type";
    /**
     * constructor for exception
     */
    public invalidVariableTypeException() {
        super(DEFAULT_MESSAGE);
    }
}
