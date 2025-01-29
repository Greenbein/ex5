package code_processing.condition_exceptions;

public class IllegalScopeForIfException extends RuntimeException {
  private static final String MESSAGE = "\nYou can't use \"if\" command in the global scope.\n" +
          "It is allowed to use \"if\" command inside functions only.";
  public IllegalScopeForIfException() {
    super(MESSAGE);
  }
}
