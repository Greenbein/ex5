package variables.basic_exceptions;

/**
 * exception for invalid name stats with double underscore
 */
public class NameStartsWithDoubleUnderscoreException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid name: it cannot start with wo underscore";

    /**
     * constructor for exception
     */
    public NameStartsWithDoubleUnderscoreException() {
        super(DEFAULT_MESSAGE);
    }
}
