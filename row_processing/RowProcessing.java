package row_processing;

import databases.VariableDataBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowProcessing {
    // different variables without assignment
    //int x, y, z;
    private static final String regexOne
            = "^(int|double|String|boolean|char)\\s+\\w+" +
            "(?:\\s*,\\s*\\w+)*\\s*;\\s*$";
    // different variables someone with assignment and someone without
    //int i1, i2 = 6;
    private static final String regexTwo =
           "^(int|double|String|boolean|char)\\s+\\w+(?:\\s*(?:=\\s*\\w+)?" +
                   "\\s*,\\s*\\w+(?:\\s*=\\s*\\w+)?)*\\s*;\\s*$";

    // Single variable without or with assignment
    //int a = 5;
    //String  b  =  "hello";
    //char i1;
    //double i1 = -.3;
    private static final String regexThree =
            "^(int|double|String|boolean|char)\\s+\\w+(?:\\s*=\\" +
                    "s*(?:\\w+|[+-]?\\d*(\\.\\d+)?|\".*\"))?\\s*;\\s*$";


    private static final String regexFour =
            "^final\\s+(int|double|String|boolean|char)\\s+\\w+\\s*=\\" +
                    "s*[+-]?\\d+(?:,\\s*\\w+\\s*=\\s*[+-]?\\d+)*\\s*;$";





















    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String STRING = "String";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final ArrayList<String> TYPES_LIST = new ArrayList<>(
            Arrays.asList(DOUBLE, INTEGER, STRING, BOOLEAN, CHAR)
    );

    private final String text;
    private final int currentLayer;
    private VariableDataBase variableDataBase;
    private final int currentLine;
    public RowProcessing(String text, int currentLayer, int currentLine, VariableDataBase variableDataBase) {
        this.text = text;
        this.currentLayer = currentLayer;
        this.currentLine = currentLine;
        this.variableDataBase = variableDataBase;
    }
    private void processRowMultipleVariables() {

    }

    private void processDeclarationOrAssignment() {

    }


}
