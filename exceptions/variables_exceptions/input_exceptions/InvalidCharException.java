package exceptions.variables_exceptions.input_exceptions;
/**
 * exception for an invalid format of a char
 */
public class InvalidCharException extends RuntimeException {
    private static final String INVALID_CHAR_MESSAGE =
            "\nInvalid value for char: ";
    /**
     * constructor for exception
     */
    public InvalidCharException(String varName) {
        super(INVALID_CHAR_MESSAGE+varName);
    }
}