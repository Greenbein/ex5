package exceptions.basic_exceptions;
/**
 * this exception handles the case we iterate over all the options for a line and none of the
 * options are correct
 */
public class InvalidFormatLine extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
            " : the line is in incorrect format ";

    /**
     * constructor for exception
     * @param lineNUmber the line the constructor throwing error from
     */
    public InvalidFormatLine(int lineNUmber) {
        super(lineNUmber + DEFAULT_MESSAGE);
    }
}
