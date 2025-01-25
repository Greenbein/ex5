package variables.exceptions;

/**
 * AssignNonInitializedVariableError exception class
 */
public class AssignNonInitializedVariableError extends RuntimeException {
    private static final String DEFAULT_MESSAGE_FIRST_PART =
            "You can't assign the variable: ";
    private static final String DEFAULT_MESSAGE_SECOND_PART =
            ",if it is defined as uninitialized";

    /**
     * default constructor exception
     */
    public AssignNonInitializedVariableError(String name) {
        super(DEFAULT_MESSAGE_FIRST_PART + name + DEFAULT_MESSAGE_SECOND_PART);
    }
}