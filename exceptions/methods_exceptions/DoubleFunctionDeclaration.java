package exceptions.methods_exceptions;

/**
 * Exception in the case we want to declare two functions with the same name
 */
public class DoubleFunctionDeclaration extends RuntimeException {
  private static final String MESSAGE = "The method with the name '%s' already exists.";
  /**
   * Exception's constructor
   * @param methodName - method's name
   */
  public DoubleFunctionDeclaration(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
