package variables.exceptions;

/**
 * this exception handles the case we trying to apply value of another variable
 * that don't exist in our db
 */
public class UnreachableVariableException extends RuntimeException {
    private static final String PART_1 = " The variable called \"";
    private static final String PART_2 = "\" is unreachable or does not exist.";

    /**
     * default constructor for exception
     * @param varName name of variable causing exception
     */
    public UnreachableVariableException(String varName) {
        super(PART_1+varName+PART_2);
    }
}
