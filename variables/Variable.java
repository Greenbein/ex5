package variables;

public abstract class Variable {
    private String name;
    private int layer;
    private boolean isFinal;
    private boolean isInitialized;
    protected Variable(String name,
                       String valueStr,
                       int layer,
                       boolean isFinal,
                       boolean isInitialized) {
        if(isValidName(name)) {
            this.name = name;
        }
        this.layer = layer;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }
    public abstract boolean isValidInput(String input);
    public boolean isValidName(String name) throws IllegalArgumentException {
        // ...
        return true;
    }

    // getters
    public boolean isFinal() {
        return this.isFinal;
    }
    public boolean isInitialized() {
        return this.isInitialized;
    }
    public String getName() {
        return this.name;
    }
    public int getLayer() {
        return this.layer;
    }


}
