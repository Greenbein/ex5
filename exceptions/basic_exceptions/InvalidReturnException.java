package exceptions.basic_exceptions;

/**
 * this exception handles the case we try to return from layer 0 (global)
 */
public class InvalidReturnException extends RuntimeException {
   private static final String DEFAULT_MESSAGE =
          " : you can't return in layer 0 ";

   /**
    * constructor for exception
    * @param lineNUmber the line the constructor throwing error from
    */
    public InvalidReturnException(int lineNUmber) {
        super(DEFAULT_MESSAGE);
    }
}
