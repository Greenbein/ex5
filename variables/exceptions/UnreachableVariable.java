package variables.exceptions;

public class UnreachableVariable extends RuntimeException {
    private static final String P1 = " The variable called \"";
    private static final String P2 = "\" is unreachable or does not exist.";
    public UnreachableVariable(String varName) {
        super(P1+varName+P2);
    }
}
