package exceptions.exceptions_main;

import java.io.IOException;

/**
 * We raise it im the case the provided number of argument is incorrect
 */
public class IllegalNumberOfArguments extends IOException {
  private static final String MESSAGE = "Illegal number of arguments.";
  /**
   * IllegalNumberOfArguments constructor
   */
  public IllegalNumberOfArguments() {
        super(MESSAGE);
    }
}
