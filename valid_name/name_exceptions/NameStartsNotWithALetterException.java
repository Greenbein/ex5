package valid_name.name_exceptions;

/**
 * exception for the case the name of the method starts not with a letter
 */
public class NameStartsNotWithALetterException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The name of the method must start with a letter";
    /**
     * default constructor exception
     */
    public NameStartsNotWithALetterException() {
        super(DEFAULT_MESSAGE);
    }
}
