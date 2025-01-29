package variables.exceptions;

public class InvalidFormatException extends RuntimeException {
    private static final String MESSAGE = "Invalid format";
    public InvalidFormatException() {
        super(MESSAGE);
    }
}
