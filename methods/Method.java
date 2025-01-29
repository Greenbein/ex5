package methods;

import variables.Variable;
import variables.VariableType;

import java.util.ArrayList;

/**
 * Method class
 */
public class Method {
    private String name;
    private ArrayList<VariableType> parametersTypes;

    /**
     * Method constructor
     * @param name - method's name
     * @param parametersTypes - ArrayList of variables with default values
     */
    public Method(String name, ArrayList<VariableType> parametersTypes) {
        this.name = name;
        this.parametersTypes = parametersTypes;
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
     * @return array list of types
     */
    public ArrayList<VariableType> getParameterTypes() {
        return parametersTypes;
    }


    /**
     * toString function
     * @return String with all relevant data
     */
    public String toString() {
        String result = "method \"" + name + "\", parameters : {";

        for (int i = 0; i < parametersTypes.size(); i++) {
            VariableType type = parametersTypes.get(i);
            result += type.toString();

            if (i < parametersTypes.size() - 1) {
                result += ",";
            }
        }
        result += "}";
        return result;
    }

}
