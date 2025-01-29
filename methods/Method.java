package methods;

import variables.Variable;
import java.util.ArrayList;

/**
 * Method class
 */
public class Method {
    private String name;
    private ArrayList<Variable> parameters;

    /**
     * Method constructor
     * @param name - method's name
     * @param parameters - ArrayList of variables with default values
     */
    public Method(String name, ArrayList<Variable> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * get method's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get all method's parameters
     * @return array list of variables
     */
    public ArrayList<Variable> getParameters() {
        return parameters;
    }


    /**
     * toString function
     * @return String with all relevant data
     */
    public String toString() {
        String result = "method \"" + name + "\", parameters : {";

        for (int i = 0; i < parameters.size(); i++) {
            Variable var = parameters.get(i);
            if (var.isFinal()) {
                result += "final ";
            }
            result += var.getValueType().toString();

            if (i < parameters.size() - 1) {
                result += ",";
            }
        }

        result += "}";
        return result;
    }

}
