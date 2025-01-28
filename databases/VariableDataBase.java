package databases;

import variables.Variable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * DataBase constructor for variables
 */
public class VariableDataBase {
    private final HashMap<Integer, HashSet<Variable>> varDataBase;
    public VariableDataBase() {
        this.varDataBase = new HashMap<>();
    }

    /**
     * this function add a variable to the database
     * @param variable variable we are going to add to the database
     */
    public void addVariable(Variable variable) {
        if(!this.varDataBase.containsKey(variable.getLayer())){
            this.varDataBase.put(variable.getLayer(), new HashSet<>());
            this.varDataBase.get(variable.getLayer()).add(variable);
        }
        else{
            this.varDataBase.get(variable.getLayer()).add(variable);
        }
    }

    /**
     * this function removes a layer from the database
     * @param layer variable we are going to remove from the database
     */
    public void removeLayer(int layer) {
        this.varDataBase.remove(layer);
    }

    public String toString() {
        String result = "VariableDataBase:\n";
        for (Integer layer : varDataBase.keySet()) {
            result += "Layer " + layer + ":\n";
            for (Variable variable : varDataBase.get(layer)) {
                result += "  " + variable.toString() + "\n"; // Assumes Variable has a toString method
            }
        }
        return result;
    }

}
