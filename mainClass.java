import variables.Variable;
import variables.VariableType;

import java.lang.foreign.ValueLayout;

public class mainClass {
    public static void main(String[] args) {
        Variable var1 = new Variable("var1",
                1,
                false,
                true,
                null,
                VariableType.INTEGER,
                "1.0");
        System.out.println(var1.getValue());
    }
}
