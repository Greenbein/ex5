package code_processing.condition_exceptions;

public class InvalidFormatForWhileCommandException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Invalid format for \"while\" command";
  public InvalidFormatForWhileCommandException() {
    super(ERROR_MESSAGE);
  }
}
