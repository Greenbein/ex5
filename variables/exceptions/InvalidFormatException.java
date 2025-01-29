package variables.exceptions;

/**
 * this exception addresses the case the initialization is invalid
 */
public class InvalidFormatException extends RuntimeException {
    private static final String MESSAGE = "Invalid format of initialization of the variable.";

    /**
     * default constructor of the exception
     */
    public InvalidFormatException() {
        super(MESSAGE);
    }
}
