package exceptions.variables_exceptions.basic_variable_exception;

/**
 * this exception handles the case we are trying
 * to assign invalid value to the variable
 */
public class InvalidVariableAssignmentException extends RuntimeException {
    private static final String P1 = " Assignment error. The variable called \"";
    private static final String P2 = "\" is not available or does not exist.";

    /**
     * default exception for exception
     * @param name name of variable causing exception
     */
    public InvalidVariableAssignmentException(String name) {
        super(P1+name+P2);
    }
}
