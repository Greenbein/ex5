package exceptions.basic_exceptions.condition_exceptions;

/**
 * this exception addresses the case we use invalid format for while
 */
public class InvalidFormatForWhileCommandException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Invalid format for \"while\" command";

  /**
   * default constructor exception
   */
  public InvalidFormatForWhileCommandException() {
    super(ERROR_MESSAGE);
  }
}
