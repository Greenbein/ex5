package code_processing;

import databases.VariableDataBase;
import variables.Variable;
import variables.VariableType;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class RowProcessing {

    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String CHAR = "char";
    private static final String STRING = "String";
    private static final String BOOLEAN = "boolean";

//    private static final String OPERATOR_CASE = "('.*?'|\".*?\"|true|false|[+-]?(\\d*(\\.\\d+)?))\\s*[\\+\\-\\*\\/]?\\s*('.*?'|\".*?\"|true|false|[+-]?(\\d*(\\.\\d+)?))";
//    private static final String INPUT_REGEX = "("+OPERATOR_CASE+"\'.*?\'|\".*?\"|\\w+|true|false|[+-]*(\\d+|\\d*.\\d*))";
    private static final String INPUT_REGEX = "(\'.*?\'|\".*?\"|\\w+|true|false|[+-]*(\\d+|\\d*.\\d*))";

    private static final String TYPES_REGEX = "\\s*(int|double|String|boolean|char)";
    private static final String VAR_NAME_REGEX = "\\w+";
    private static final String EXTRACT_DATA_MIXED = "((int|double|String|boolean|char)|\\w+\\s*=\\s*('.*?'|\\w+|\".*?\"|true|false|[+-]?(\\d*(\\.\\d+)?))\\s*|\\w+)";
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
    private static final String STARTS_WITH_FINAL = "^\\s*final\\b.*";
    private static final String STARTS_WITH_VAR_TYPE = "^\\s*(int|double|String|boolean|char)\\b.*";

    private final String code;
    private final int currentLayer;
    private VariableDataBase variableDataBase;
    private final int currentLine;
    public RowProcessing(String code, int currentLayer, int currentLine, VariableDataBase variableDataBase) {
        this.code = code;
        this.currentLayer = currentLayer;
        this.currentLine = currentLine;
        this.variableDataBase = variableDataBase;
    }

    private void processCode(){
        if(isCorrectFormatFinal(this.code)){
            extractDataFinal(this.code);
        }
        else if (isMixed(this.code)){
            extractDataMixed(this.code);
        }
    }


    private String extractStringAfterWord(String code, String word) {
        int index = code.indexOf(word);
        if (index == -1) return code; // Return the original string if the word is not found
        return code.substring(index + word.length()).trim();
    }

    private boolean startsWithPattern(String code, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    //-----------------------Final-------------------------
    private boolean isCorrectFormatFinal(String code) {
        if (startsWithPattern(code, STARTS_WITH_FINAL)) {
            String secondPartOfCode = extractStringAfterWord(code, "final");
            // we checked mixed because we want to check is there a variable
            // not initialized in variable and throw there the specific exception
            Pattern pattern = Pattern.compile(MIXED);
            Matcher matcher = pattern.matcher(secondPartOfCode);
            return matcher.matches();
        }
        return false;
    }
    //------------------------------------------------------

    //-------------------definition or initialization of variables------
    private boolean isMixed(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(MIXED);
            Matcher matcher = patternMixed.matcher(code);
            System.out.println(matcher.matches());
            return matcher.matches();
        }
        return false;
    }

    private boolean isInitializationOnly(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(INITIALIZED_ONLY);
            Matcher matcher = patternMixed.matcher(code);
            return matcher.matches();
        }
        return false;
    }
//
//    private boolean isDefinitionOnly(String code) {
//        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
//            Pattern patternMixed = Pattern.compile(UNINITIALIZED_ONLY);
//            Matcher matcher = patternMixed.matcher(code);
//            return matcher.matches();
//        }
//        return false;
//    }
    // -------------------extract data final -----------------------------
    private void extractDataFinal(String code) {
        extractNewVarsFromRow(code,true,1);
    }
    // -------------------extract data mixed -----------------------------
    private void extractDataMixed(String code) {
        extractNewVarsFromRow(code,false,0);
    }

    //--------------------extract data from string -----------------
    private void extractNewVarsFromRow(String code, boolean isFinal, int typeWordIndex){
        Pattern pattern = Pattern.compile(EXTRACT_DATA_MIXED);
        Matcher matcher = pattern.matcher(code);
        ArrayList<String> subStrings = new ArrayList<>();
        while (matcher.find()) {
            subStrings.add(matcher.group());
        }
        String typeWord = subStrings.get(typeWordIndex);
        if(typeWordIndex>0){
            subStrings.remove(typeWordIndex-1);
        }
        System.out.println("SUB STRINGS:" +subStrings);
        addNewVars(typeWord,subStrings,this.currentLayer,isFinal,this.variableDataBase);
    }

    private void addNewVars(String typeStr,
                                            ArrayList<String> subStrings,
                                            int layer, boolean isFinal,
                                            VariableDataBase variableDataBase){
        VariableType type = VariableType.fromString(typeStr);
        for (int i = 1; i < subStrings.size(); i++) {
            createVariable(type, subStrings.get(i), layer, isFinal,variableDataBase);
        }
    }

    private void createVariable(VariableType type, String code, int layer,  boolean isFinal,VariableDataBase variableDataBase) {
        code = code.trim();
        if (code.contains("=")) {
            String[] subWords = code.split("=");
            String varName = subWords[0].trim();
            String value = subWords[1].trim();

//            System.out.println("VAR NAME: "+varName+" VALUE: "+value);
            Variable variable = new Variable(varName,layer,isFinal,true,type, value);
            variableDataBase.addVariable(variable);
        } else {
            String varName = code;
            Variable variable = new Variable(varName,layer,isFinal,type);
            variableDataBase.addVariable(variable);
        }
    }

    public static void main(String[] args) {
        String code1 = "int a = 2;";
        System.out.println(code1);
        VariableDataBase variableDataBase1 = new VariableDataBase();
        RowProcessing rowProcessing1 = new RowProcessing(code1, 1, 1, variableDataBase1);
        rowProcessing1.processCode();
        System.out.println(variableDataBase1);

    }

}
