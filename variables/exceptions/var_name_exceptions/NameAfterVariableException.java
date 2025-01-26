package variables.exceptions.var_name_exceptions;

/**
 * exception for the case name is equal to variable type
 */
public class NameAfterVariableException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            "Not allowed: name can't be equal to variable type";

    /**
     * constructor for exception
     */
    public NameAfterVariableException() {
        super(DEFAULT_MESSAGE);
    }
}
