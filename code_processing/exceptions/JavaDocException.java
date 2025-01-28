package code_processing.exceptions;

/**
 * exception for the case we java doc comment
 */
public class JavaDocException extends RuntimeException {
    private static final String MESSAGE =
            " : Java doc comments are not allowed.";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public JavaDocException(int line) {
        super(line+MESSAGE);
    }
}
