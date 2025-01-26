package row_processing.exceptions;

/**
 * exception for the case we use operators
 */
public class InvalidUseOperatorsExceptions extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : invalid use of operators ";

    /**
     * constructor for exception
     * @param line the line the constructor throwing error from
     */
    public InvalidUseOperatorsExceptions(int line) {
        super(line + DEFAULT_MESSAGE);
    }
}
