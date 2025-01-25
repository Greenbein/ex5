package variables.exceptions;

/**
 *  Exception for a name that is exactly an underscore (_),
 *  with no additional characters.
 */
public class NameIsUnderscoreException extends RuntimeException {
  private static final String DEFAULT_MESSAGE =
          "Invalid name: if it starts with an underscore, it must contain at " +
                  "least one additional character.";

  /**
   * constructor for exception
   */
    public NameIsUnderscoreException() {
        super(DEFAULT_MESSAGE);
    }
}
