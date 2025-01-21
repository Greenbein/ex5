package variables.basic_exceptions;

/**
 * exception for an invalid format of a name
 */
public class InvalidFormatName extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid name: invalid format of a name contains invalid characters";
    /**
     * constructor for exception
     */
    public InvalidFormatName() {
        super(DEFAULT_MESSAGE);
    }
}
