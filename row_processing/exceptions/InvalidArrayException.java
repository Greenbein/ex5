package row_processing.exceptions;

public class InvalidArrayException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : array are not allowed to be used ";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public InvalidArrayException(int line) {
        super(line+DEFAULT_MESSAGE);
    }
}
