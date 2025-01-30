package exceptions.basic_exceptions.condition_exceptions;
/**
 * this exception addresses the case of trying to use if command
 * in global scope
 */
public class IllegalScopeForIfException extends RuntimeException {
  private static final String MESSAGE =
          "\nYou can't use \"if\" command in the global scope.\n" +
          "It is allowed to use \"if\" command inside functions only.";
  /**
   * default constructor exception
   */
  public IllegalScopeForIfException() {
    super(MESSAGE);
  }
}
