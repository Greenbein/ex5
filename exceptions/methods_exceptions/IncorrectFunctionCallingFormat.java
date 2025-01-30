package exceptions.methods_exceptions;

/**
 * We use this exception when user tries to call a function, but the syntax is incorrect
 */
public class IncorrectFunctionCallingFormat extends RuntimeException {
    private static final String MESSAGE = "There is a line with incorrect method calling syntax";
    /**
     * IncorrectFunctionCallingFormat constructor
     */
    public IncorrectFunctionCallingFormat() {
        super(MESSAGE);
    }
}
