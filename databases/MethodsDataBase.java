package databases;

import methods.Method;
import java.util.ArrayList;

/**
 * Class for methods database
 */
public class MethodsDataBase {
    private ArrayList<Method>methods;

    /**
     * Methods database constructor
     */
    public MethodsDataBase() {
        methods = new ArrayList<>();
    }

    /**
     * check if a method with the given name already exists in the database
     * @param methodName - method's nam
     * @return boolean
     */
    public boolean isExist(String methodName) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a method from the database according to its name
     * @param methodName - method's name
     * @return Method object
     */
    public Method getMethod(String methodName) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Add a new method to the database
     * @param m - Method object
     */
    public void addMethod(Method m) {
        methods.add(m);
    }

    /**
     * toString method for methodsDataBase
     * @return String
     */
    public String toString(){
        if (methods.isEmpty()) {
            return "Methods Database:\nNo methods stored.";
        }

        String result = "Methods Database:\n";
        for (Method m : methods) {
            result += m.toString() + "\n";
        }

        return result;
    }
}
