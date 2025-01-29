package methods;

import variables.Variable;

import java.util.ArrayList;

public class Method {

    private String name;
    private ArrayList<Variable> parameters;
    public Method(String name, ArrayList<Variable> parameters) {
        this.name = name;
        this.parameters = parameters;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Variable> getParameters() {
        return parameters;
    }


}
