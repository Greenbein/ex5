package methods.exceptions;

public class IncorrectNumberOfParameters extends RuntimeException {
    private static final String MESSAGE = "Method '%s' gets %d parameters, but got %d.";
    public IncorrectNumberOfParameters(String methodName,
                                       int realNumberOfParameters,
                                       int currentNumberOfParameters) {
        super(String.format(MESSAGE, methodName, realNumberOfParameters, currentNumberOfParameters));
    }
}
