package exceptions.methods_exceptions;

/**
 * Exception in the case the method receives illegal amount of parameters
 */
public class IncorrectNumberOfParameters extends RuntimeException {
    private static final String MESSAGE = "Method '%s' gets %d parameters, but got %d.";

    /**
     * IncorrectNumberOfParameters constructor
     * @param methodName - the name of method
     * @param realNumberOfParameters - expected number of parameters
     * @param currentNumberOfParameters - gotten number of parameters
     */
    public IncorrectNumberOfParameters(String methodName,
                                       int realNumberOfParameters,
                                       int currentNumberOfParameters) {
        super(String.format(MESSAGE, methodName, realNumberOfParameters, currentNumberOfParameters));
    }
}
