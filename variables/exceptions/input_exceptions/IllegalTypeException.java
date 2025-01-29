package variables.exceptions.input_exceptions;

/**
 * We use this exception in the case we want to create a new variable with illegal type;
 */
public class IllegalTypeException extends RuntimeException {
  private static final String MESSAGE = "\nThe type 's%' is illegal.\n " +
          "You are allowed to use int,double,char,String and boolean only.";
  /**
   * IllegalTypeException construction
   * @param type - gotten type
   */
    public IllegalTypeException(String type) {
        super(String.format(MESSAGE, type));
    }
}
