package exceptions.basic_exceptions;

/**
 * this exception handles the case we use single comment line not in the start
 */
public class InvalidSingleCommentLineException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : single comment line not in the start of the sentence is invalid ";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public InvalidSingleCommentLineException(int line) {
        super(line + DEFAULT_MESSAGE);
    }
}
