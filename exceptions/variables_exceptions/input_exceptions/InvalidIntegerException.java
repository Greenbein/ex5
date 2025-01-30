package exceptions.variables_exceptions.input_exceptions;

/**
 * this exception handles the case we apply invalid value for integer
 */
public class InvalidIntegerException extends RuntimeException {
    private static final String INVALID_INTEGER_MESSAGE =
            "\nInvalid value for integer: ";

    /**
     * default constructor for exception
     * @param varName variable name that causing exception
     */
    public InvalidIntegerException(String varName) {
        super(INVALID_INTEGER_MESSAGE+varName);
    }
}
