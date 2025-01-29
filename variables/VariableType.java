package variables;

/**
 * this enum implements variable type for variable
 */
public enum VariableType{
    INTEGER, DOUBLE, BOOLEAN, STRING, CHAR;

    /**
     *
     * @return this function returns lower case of enum
     */
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * this functions switches type to enum
     * @param type type of variable
     * @return enum variable type
     */
    public static VariableType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        try {
            if(type.equals("int")){
                return VariableType.INTEGER;
            }
            return VariableType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid VariableType: " + type);
        }
    }
}

