package variables.exceptions;

/**
 * this exception handles the case we trying to assign invalid value to the variable
 */
public class InvalidVariableAssignment extends RuntimeException {
    private static final String P1 = " Assignment error. The variable called \"";
    private static final String P2 = "\" is not available or does not exist.";

    /**
     * default exception for exception
     * @param name name of variable causing exception
     */
    public InvalidVariableAssignment(String name) {
        super(P1+name+P2);
    }
}
