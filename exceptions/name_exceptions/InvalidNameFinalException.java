package exceptions.name_exceptions;

/**
 * exception for the case name is final
 */
public class InvalidNameFinalException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Not allowed: name can't be final";

    /**
     * constructor for exception
     */
    public InvalidNameFinalException() {
        super(DEFAULT_MESSAGE);
    }
}
