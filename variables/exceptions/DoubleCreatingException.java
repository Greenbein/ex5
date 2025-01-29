package variables.exceptions;

public class DoubleCreatingException extends RuntimeException {
  private static final String P1= "The variable called: ";
  private static final String P2= " already exists.";
    public DoubleCreatingException(String varName) {
        super(P1+varName+P2);
    }
}
