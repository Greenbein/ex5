package valid_name.name_exceptions;

/**
 * exception for a name that starts with a number
 */
public class NameStartsWithNumberException
        extends RuntimeException {
  private static final String DEFAULT_MESSAGE =
          "Invalid name: it cannot start with a number.";
    /**
     * constructor for exception
     */
    public NameStartsWithNumberException() {
        super(DEFAULT_MESSAGE);
    }
}
