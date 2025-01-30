package exceptions.basic_exceptions;

/**
 * there are more closing brackets } then opening brackets {
 */
public class InvalidAmountOfClosingBracketsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : there are more closing brackets } then opening brackets { ";

    /**
     * constructor for exception
     * @param lineNumber the line the constructor throwing error from
     */
    public InvalidAmountOfClosingBracketsException(int lineNumber) {
        super(lineNumber + DEFAULT_MESSAGE);
    }
}
