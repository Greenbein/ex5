package code_processing.condition_exceptions;

import variables.VariableType;

public class InvalidVarTypeForConditionException extends RuntimeException {
    private static final String P1 = "The variable called ";
    private static final String P2 = " is ";
    private static final String P3 = " .In JAVAS conditions you are allowed to use only int,double and boolean variables.";
    public InvalidVarTypeForConditionException(String varName, VariableType type) {
        super(P1+varName+P2+type+P3);
    }
}
