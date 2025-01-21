package variables.double_s_java.exceptions;
/**
 * exception for an invalid format of a double
 */
public class InvalidDoubleException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid double: invalid format of a double";
    /**
     * constructor for exception
     */
    public InvalidDoubleException() {
        super(DEFAULT_MESSAGE);
    }
}
