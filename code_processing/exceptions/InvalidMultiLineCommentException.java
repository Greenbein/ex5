package code_processing.exceptions;

/**
 * exception for the case we find multi line comment
 */
public class InvalidMultiLineCommentException extends RuntimeException {
  private static final String DEFAULT_MESSAGE =
          " :multi-line comments are invalid ";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public InvalidMultiLineCommentException(int line) {
        super(line + DEFAULT_MESSAGE);
    }
}
