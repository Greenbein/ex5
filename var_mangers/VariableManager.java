package var_mangers;

public interface VariableManager {
    boolean isValidInput(String input);
    Object initializeValue(String input);
    Object setValue(String input, Object currentValue, boolean isFinal);
}
