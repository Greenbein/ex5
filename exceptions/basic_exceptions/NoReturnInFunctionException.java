package exceptions.basic_exceptions;
/**
 * this exception handles the case we finished the method without the line return;
 */
public class NoReturnInFunctionException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : finished method without the line return; in external layer ";

    /**
     * constructor for exception
     * @param lineNUmber the line the constructor throwing error from
     */
    public NoReturnInFunctionException(int lineNUmber) {
        super(lineNUmber + DEFAULT_MESSAGE);
    }
}
