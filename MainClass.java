import variables.Variable;
import variables.VariableType;

public class MainClass {
    public static void main(String[] args) {
        Variable var1 = new Variable("var1",
                1,
                true,
                true,
                VariableType.STRING,
                "aaa");
        System.out.println(var1.getValue());
    }
}
