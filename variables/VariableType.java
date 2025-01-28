package variables;

public enum VariableType{
    INTEGER, DOUBLE, BOOLEAN, STRING, CHAR;
    public String toString() {
        // Return a lowercase version of the enum value
        return this.name().toLowerCase();
    }
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

