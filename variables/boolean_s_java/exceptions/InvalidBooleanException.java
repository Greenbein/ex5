package variables.boolean_s_java.exceptions;

/**
 * class of boolean exception
 */
public class InvalidBooleanException extends RuntimeException {
    private static final String INVALID_BOOLEAN_INPUT =
          "Invalid value for boolean variable called ";

  /**
   * constructor of boolean exception
   */
  public InvalidBooleanException() {
        super(INVALID_BOOLEAN_INPUT);
    }
}
