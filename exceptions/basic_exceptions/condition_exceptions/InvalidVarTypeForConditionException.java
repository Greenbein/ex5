package exceptions.basic_exceptions.condition_exceptions;

import variables.VariableType;

/**
 * this exception addresses the case we try to use variables
 * that are not int/boolean/double
 */
public class InvalidVarTypeForConditionException extends RuntimeException {
    private static final String P1 = "The variable called ";
    private static final String P2 = " is ";
    private static final String P3 = " .In JAVAS conditions you are allowed to use only int," +
            "double and boolean variables.";

    /**
     * default constructor exception
     * @param varName variable name causing exception
     * @param type the type of the variable causing the exception
     */
    public InvalidVarTypeForConditionException(String varName, VariableType type) {
        super(P1+varName+P2+type+P3);
    }
}
