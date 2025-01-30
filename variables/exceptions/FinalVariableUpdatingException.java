package variables.exceptions;

/**
 * this exception handles the case we are
 * trying to apply new value to a finalised variable
 */
public class FinalVariableUpdatingException extends RuntimeException {
  private static final String P1= "The variable called: ";
  private static final String P2= " ,cannot be updated, " +
          "because it is final and already initialized";
    /**
     * default constructor for exception
     * @param varName name of variable causing exception
     */
    public FinalVariableUpdatingException(String varName) {
        super(P1+varName+P2);
    }
}
