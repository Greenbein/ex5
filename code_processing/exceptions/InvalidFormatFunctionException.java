package code_processing.exceptions;

/**
 * this exception addresses the case the format of the function is incorrect
 */
public class InvalidFormatFunctionException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid format for a function";
    /**
     * constructor for exception
     */
    public InvalidFormatFunctionException() {
        super(DEFAULT_MESSAGE);
    }
}
