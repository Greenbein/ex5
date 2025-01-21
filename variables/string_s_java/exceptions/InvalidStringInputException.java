package variables.string_s_java.exceptions;

public class InvalidStringInputException extends RuntimeException {
    private static final String INVALID_STRING_INPUT =
            "\nInvalid value for string variable called ";
    public InvalidStringInputException(String varName) {
        super(INVALID_STRING_INPUT+varName);
    }
}
