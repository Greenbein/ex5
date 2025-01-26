package variables.exceptions;

public class InvalidIntegerException extends RuntimeException {
    private static final String INVALID_INTEGER_MESSAGE =
            "\nInvalid value for integer: ";
    public InvalidIntegerException(String varName) {
        super(INVALID_INTEGER_MESSAGE+varName);
    }
}
