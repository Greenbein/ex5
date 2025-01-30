package exceptions.variables_exceptions.input_exceptions;

/**
 * this exception handles the case we apply invalid value for string
 */
public class InvalidStringException extends RuntimeException {
    private static final String INVALID_STRING_MESSAGE =
            "\nInvalid value for string: ";
    /**
     * default constructor for exception
     * @param varName variable name that causing exception
     */
    public InvalidStringException(String varName) {
        super(INVALID_STRING_MESSAGE + varName);
    }
}
