package variables.exceptions;

public class InvalidVariableAssignment extends RuntimeException {
    private static final String P1 = " Assignment error. The variable called \"";
    private static final String P2 = "\" is not available or does not exist.";
    public InvalidVariableAssignment(String name) {
        super(P1+name+P2);
    }
}
