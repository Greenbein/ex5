package variables.exceptions;

public class FinalVariableUpdatingException extends RuntimeException {
  private static final String P1= "The variable called: ";
  private static final String P2= " ,cannot be updated, because it is final and already initialized";
    public FinalVariableUpdatingException(String varName) {
        super(P1+varName+P2);
    }
}
