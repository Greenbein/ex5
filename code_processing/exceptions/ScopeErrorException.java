package code_processing.exceptions;

/**
 * We use it when there is an imbalance of '{' and '}'
 */
public class ScopeErrorException extends RuntimeException {
    private static final String ERROR_MESSAGE = "\nScope error. Check that if all scopes are " +
            "closed or there is redundant closing ";

    /**
     * ScopeErrorException constructor
     */
    public ScopeErrorException() {
        super(ERROR_MESSAGE);
    }
}
