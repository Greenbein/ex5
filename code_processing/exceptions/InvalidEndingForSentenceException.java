package code_processing.exceptions;

/**
 * Exception for the case of an invalid sentence ending
 * (not involving ';', '{', or '}').
 */
public class InvalidEndingForSentenceException extends RuntimeException {
  private static final String DEFAULT_MESSAGE =
          "Invalid ending for sentence (must end with ';', '{', or '}').";
  /**
   * constructor for exception
   * @param line the line the constructor throwing error from
   */
    public InvalidEndingForSentenceException(int line) {
        super(line + DEFAULT_MESSAGE);
    }
}
