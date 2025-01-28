package row_processing;

import databases.VariableDataBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowProcessing {
//     private static final String INPUT_REGEX = "[^\\s*]+";
    private static final String INPUT_REGEX = "(\'.*\'|\".*\"|\\w+|[+-]*(\\d+|\\d*.\\d*))";
    private static final String TYPES_REGEX = "\\s*(int|double|String|boolean|char)";
    private static final String VAR_NAME_REGEX = "\\w+";

//    private static final String MIXED_SIMPLE = "^(int|double|String|char|boolean)\\s+(\\w+\\s*,\\s*|\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*,\\s*)*(\\w+\\s*|\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*);$";
//    private static final String INITIALIZED_SIMPLE = "^(int|double|String|char|boolean)\\s+(\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*,\\s*)*(\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*);$";
//    private static final String UNINITIALIZED_SIMPLE = "^(int|double|String|char|boolean)\\s+(\\w+\\s*,\\s*)*(\\w+\\s*);$";
//    private static final String FINAL_SIMPLE ="^final\\s+(int|double|String|char|boolean)\\s+(\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*,\\s*)*(\\w+\\s*=\\s*(\".*?\"|\\w+|[+,-]*(\\d|(\\d*.\\d+)))\\s*);$";

    private static final String MIXED =
            "^"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*,\\s*|"
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*|"
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*);$";

    private static final String INITIALIZED_ONLY = "^"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*);$";

    private static final String UNINITIALIZED_ONLY = "^"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*);$";
    private static final String FINAL = "^final\\s+"
            +TYPES_REGEX
            +"\\s+("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*,\\s*)*("
            +VAR_NAME_REGEX
            +"\\s*=\\s*"
            +INPUT_REGEX
            +"\\s*);$";
    private static final String STARTS_WITH_FINAL = "^\\s*final\\s+";
    private static final String STARTS_WITH_VAR_TYPE = "^\\s*(int|double|String|boolean|char)\\b.*";

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




    public static String extractStringAfterWord(String code, String word) {
        int index = code.indexOf(word);
        if (index == -1) return code; // Return the original string if the word is not found
        return code.substring(index + word.length()).trim();
    }

    public static boolean startsWithPattern(String code, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    //-----------------------Final-------------------------
    public static boolean isCorrectFormatFinal(String code) {
        if (startsWithPattern(code, STARTS_WITH_FINAL)) {
            String secondPartOfCode = extractStringAfterWord(code, "final");
            Pattern pattern = Pattern.compile(INITIALIZED_ONLY);
            Matcher matcher = pattern.matcher(secondPartOfCode);
            return matcher.matches();
        }
        return false;
    }
    //------------------------------------------------------

    //-------------------definition or initialization of variables------
    public static boolean isMixed(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(MIXED);
            Matcher matcher = patternMixed.matcher(code);
            return matcher.matches();
        }
        return false;
    }

    public static boolean isInitializationOnly(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(INITIALIZED_ONLY);
            Matcher matcher = patternMixed.matcher(code);
            return matcher.matches();
        }
        return false;
    }

    public static boolean isDefinitionOnly(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(UNINITIALIZED_ONLY);
            Matcher matcher = patternMixed.matcher(code);
            return matcher.matches();
        }
        return false;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(STARTS_WITH_VAR_TYPE);
        String test = "double a=3, b ;";
        System.out.println(isMixed(test));

    }


}
