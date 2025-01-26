package variables.exceptions;

public class JavaDocException extends RuntimeException {
    private static final String MESSAGE =
            " : Java doc is not allowed.";
    public JavaDocException(int line) {
        super(line+MESSAGE);
    }
}
