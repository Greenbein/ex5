package valid_name.name_exceptions;

/**
 * exception for an invalid format of a name
 */
public class InvalidFormatNameException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Invalid name: invalid format of a name contains invalid characters";
    /**
     * constructor for exception
     */
    public InvalidFormatNameException() {
        super(DEFAULT_MESSAGE);
    }
}
