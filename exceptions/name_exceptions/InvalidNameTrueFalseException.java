package exceptions.name_exceptions;

/**
 * this function checks is name of variable true/false (invalid)
 */
public class InvalidNameTrueFalseException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Not allowed: name can't be true or false";
    /**
     * constructor for exception
     */
    public InvalidNameTrueFalseException() {
        super(DEFAULT_MESSAGE);
    }
}
