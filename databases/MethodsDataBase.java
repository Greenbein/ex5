package databases;

import methods.Method;

import java.util.ArrayList;

public class MethodsDataBase {
    ArrayList<Method>methods;
    public MethodsDataBase() {
        methods = new ArrayList<>();
    }

    public boolean isExist(String methodName) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }
    public Method getMethod(String methodName) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        return null;
    }
}
