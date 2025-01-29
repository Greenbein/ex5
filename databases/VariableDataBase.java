package databases;

import variables.Variable;
import variables.VariableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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

    /**
     * this function searches in the database it there a variable with
     * the same name and type in order to use to apply his value to variable
     * @param varName name of variable
     * @param myLayer layer of variable
     * @param myType type of variable
     * @return return the variable with this name and type if exist if not return null
     */
    public Variable findVarByNameAndType(String varName,
                                         int myLayer,
                                         VariableType myType) {
        for (int layer=myLayer; layer>=0;layer--){
            if(this.varDataBase.containsKey(layer)){
                HashSet<Variable> vars = this.varDataBase.get(layer);
                for (Variable var : vars) {
                    if(var.getName().equals(varName) && var.isInitialized() &&
                            var.getValueType().equals(myType)) {
                        return var;
                    }
                }
            }
        }
        return null;
    }

    /**
     * this function searches in the database it there a variable with
     * the same name
     * @param varName the variable name we search
     * @param myLayer layer we starting to search from
     * @return the variable if we find it else null
     */
    public Variable findVarByNameOnly(String varName, int myLayer) {
        for (int layer=myLayer; layer>=0;layer--){
            if(this.varDataBase.containsKey(layer)){
                HashSet<Variable> vars = this.varDataBase.get(layer);
                for (Variable var : vars) {
                    if(var.getName().equals(varName)) {
                        return var;
                    }
                }
            }
        }
        return null;
    }

    // need to check with lior if we really need these functions

    private ArrayList<Integer>relevantKeys(int layer){
        ArrayList<Integer>myKeys = new ArrayList<>();
        for(int i=layer; i>=0;i--){
            if(this.varDataBase.containsKey(i)){
                myKeys.add(i);
            }
        }
        return myKeys;
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
