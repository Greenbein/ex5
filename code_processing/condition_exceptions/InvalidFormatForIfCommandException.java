package code_processing.condition_exceptions;

public class InvalidFormatForIfCommandException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Invalid format for \"if\" command";
    public InvalidFormatForIfCommandException() {
        super(ERROR_MESSAGE);
    }
}
