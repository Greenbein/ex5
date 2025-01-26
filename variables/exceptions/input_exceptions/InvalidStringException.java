package variables.exceptions.input_exceptions;

public class InvalidStringException extends RuntimeException {
    private static final String INVALID_STRING_MESSAGE =
            "\nInvalid value for string: ";
    public InvalidStringException(String varName) {
        super(INVALID_STRING_MESSAGE + varName);
    }
}
