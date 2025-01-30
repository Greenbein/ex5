package exceptions.basic_exceptions;

/**
 * this exception addresses the case we had invalid variable type
 */
public class invalidVariableTypeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : invalid variable type";
    /**
     * constructor for exception
     */
    public invalidVariableTypeException(String variableType) {
        super(variableType + DEFAULT_MESSAGE);
    }
}
