package variables.exceptions;

/**
 * exception for the case we are trying to initialize a variable with the
 * same name as a variable in our data base
 */
public class DoubleCreatingException extends RuntimeException {
  private static final String P1= "The variable called: ";
  private static final String P2= " already exists.";

    /**
     * default constructor exception
     * @param varName the variable name causing the exception
     */
    public DoubleCreatingException(String varName) {
        super(P1+varName+P2);
    }
}
