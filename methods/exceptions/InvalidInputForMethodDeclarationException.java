package methods.exceptions;

/**
 * Exception for the case the function declaration failed because of illegal input format in ()
 */
public class InvalidInputForMethodDeclarationException extends RuntimeException {
    private static final String MESSAGE = "\nThe declaration of method '%s' failed " +
            "because of invalid parameters declaration in brackets ().\n " +
            "The declaration has to be in a form (type name, type name2,...),\n" +
            "when allowed types are int,double,String,char and boolean." ;
    /**
     * InvalidInputForMethodDeclarationException constructor
     * @param methodName -  new method's name
     */
    public InvalidInputForMethodDeclarationException(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
