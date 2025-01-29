package variables.exceptions;

public class UpdateValueTypeErrorException extends RuntimeException {
    private static final String F1= "The variable called: ";
    private static final String F2= " ,is ";
    private static final String F3 = ",so it cannot get ";
    public UpdateValueTypeErrorException(String name, String myType, String otherType) {
        super(F1+name+F2+myType+F3+otherType);
    }
}
