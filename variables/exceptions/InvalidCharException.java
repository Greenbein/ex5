package variables.exceptions;
/**
 * exception for an invalid format of a char
 */
public class InvalidCharException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid char: invalid format of a char called ";
    /**
     * constructor for exception
     */
    public InvalidCharException(String varName) {
        super(DEFAULT_MESSAGE+varName);
    }
}