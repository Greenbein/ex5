package variables.exceptions;

/**
 * We use it when we want to get VariableType from type name
 * For example 'int' is fine, but 'innt' will lead to exception
 */
public class IllegalTypeException extends RuntimeException {
    private static final String MESSAGE = "The type 's%' is illegal";
    /**
     * IllegalTypeException constructor
     * @param type - given type
     */
    public IllegalTypeException(String type) {
        super(String.format(MESSAGE, type));
    }
}
