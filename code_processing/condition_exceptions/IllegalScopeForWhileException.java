package code_processing.condition_exceptions;

public class IllegalScopeForWhileException extends RuntimeException {
    private static final String MESSAGE = "\nYou can't use \"while\" command in the global scope.\n" +
            "It is allowed to use \"while\" command inside functions only.";
    public IllegalScopeForWhileException() {
        super(MESSAGE);
    }
}
