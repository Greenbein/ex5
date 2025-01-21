package variables.basic_exceptions;

public class InvalidVariableName extends RuntimeException {
    public InvalidVariableName(String message) {
        super(message);
    }
}
