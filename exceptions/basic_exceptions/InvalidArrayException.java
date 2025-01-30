package exceptions.basic_exceptions;

/**
 * exception for the case we locate an array in one of the lines
 */
public class InvalidArrayException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : array are not supported in Sjava ";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public InvalidArrayException(int line) {
        super(line+DEFAULT_MESSAGE);
    }
}
