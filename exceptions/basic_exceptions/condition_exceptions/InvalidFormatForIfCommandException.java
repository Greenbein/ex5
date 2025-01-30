package exceptions.basic_exceptions.condition_exceptions;

/**
 * this exception addresses the case pf invalid format of if condition
 */
public class InvalidFormatForIfCommandException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Invalid format for \"if\" command";

    /**
     * default constructor for exception
     */
    public InvalidFormatForIfCommandException() {
        super(ERROR_MESSAGE);
    }
}
