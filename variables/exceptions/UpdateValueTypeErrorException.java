package variables.exceptions;

/**
 * this exception handles the case we trying apply an input of the wrong
 * type to the variable
 */
public class UpdateValueTypeErrorException extends RuntimeException {
    private static final String F1= "The variable called: ";
    private static final String F2= " ,is ";
    private static final String F3 = ",so it cannot get ";

    /**
     * default constructor for exception
     * @param name name of the variable
     * @param myType type of the variable
     * @param otherType type of value we trying to apply to variable
     */
    public UpdateValueTypeErrorException(String name, String myType, String otherType) {
        super(F1+name+F2+myType+F3+otherType);
    }
}
