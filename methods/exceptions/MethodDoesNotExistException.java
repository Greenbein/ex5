package methods.exceptions;

public class MethodDoesNotExistException extends RuntimeException {
    private static final String MESSAGE = "Method '%s' does not exist";
    public MethodDoesNotExistException(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
