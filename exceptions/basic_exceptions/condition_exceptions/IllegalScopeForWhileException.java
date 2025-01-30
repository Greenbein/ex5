package exceptions.basic_exceptions.condition_exceptions;

/**
 * this exception addresses the case of trying to use while command
 * in global scope
 */
public class IllegalScopeForWhileException extends RuntimeException {
    private static final String MESSAGE =
            "\nYou can't use \"while\" command in the global scope.\n" +
            "It is allowed to use \"while\" command inside functions only.";

    /**
     * default constructor exception
     */
    public IllegalScopeForWhileException() {
        super(MESSAGE);
    }
}
