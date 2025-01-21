package variables.integer_s_java.exceptions;

public class InvalidIntegerInputException extends RuntimeException {
    private static final String INVALID_INTEGER_INPUT =
            "\nInvalid value for integer: ";
    public InvalidIntegerInputException(String varName) {
        super(INVALID_INTEGER_INPUT+varName);
    }
}
