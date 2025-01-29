package methods.exceptions;

/**
 * We raise this exception in the case there is no method with the provided name
 */
public class MethodDoesNotExistException extends RuntimeException {
    private static final String MESSAGE = "Method '%s' does not exist";

    /**
     * MethodDoesNotExistException constructor
     * @param methodName - method's name
     */
    public MethodDoesNotExistException(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
