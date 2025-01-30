package exceptions.variables_exceptions.input_exceptions;

/**
 * class of boolean exception
 */
public class InvalidBooleanException extends RuntimeException {
    private static final String INVALID_BOOLEAN_MESSAGE =
            "\nInvalid value for boolean: ";

  /**
   * constructor of boolean exception
   */
  public InvalidBooleanException(String varName) {
        super(INVALID_BOOLEAN_MESSAGE+varName);
    }
}
