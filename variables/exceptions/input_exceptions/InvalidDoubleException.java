package variables.exceptions.input_exceptions;
/**
 * exception for an invalid format of a double
 */
public class InvalidDoubleException extends RuntimeException {
    private static final String INVALID_DOUBLE_MESSAGE =
            "\nInvalid value for double: ";
    /**
     * constructor for exception
     */
    public InvalidDoubleException(String varName) {
        super(INVALID_DOUBLE_MESSAGE+varName);
    }
}
