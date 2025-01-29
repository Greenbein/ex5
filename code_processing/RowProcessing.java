package code_processing;

import databases.VariableDataBase;
import variables.Variable;
import variables.VariableType;
import variables.exceptions.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class process a given word
 */
public class RowProcessing {
    // ----------------------constants---------------------------------

    private static final String INPUT_REGEX =
        "(\'.*?\'|\".*?\"|[+-]?((\\d*\\.\\d*)|\\d+)|\\w+|true|false)";
    private static final String TYPES_REGEX = "\\s*(int|double|String|boolean|char)";
    private static final String VAR_NAME_REGEX = "\\w+";
    private static final String EXTRACT_DATA_MIXED =
            "((int|double|String|boolean|char)|\\w+\\s*=\\s*" +
                    "('.*?'|\".*?\"|true|false|[+-]?((\\d*\\.\\d*)|\\d+)\\s*|\\w+)|\\w+)";
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
    private static final String SETTING_ROW = "\\s*("
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
    private static final String STARTS_WITH_FINAL = "^\\s*final\\s.*";
    private static final String STARTS_WITH_VAR_TYPE =
            "^\\s*(int|double|String|boolean|char)\\s.*";
    // ------------------members of the class -----------------------------------
    private final VariableDataBase variableDataBase;

    /**
     * default constructor RowProcessing
     * @param variableDataBase given variable database
     */
    public RowProcessing(VariableDataBase variableDataBase) {
        this.variableDataBase = variableDataBase;
    }

//    private void processCode(String code, int currentLayer, int currentLine) {
//        if(isCorrectFormatFinal(code)){
//            extractDataFinal(code,currentLayer,currentLine);
//        }
//        else if (isMixed(code)){
//            extractDataMixed(code,currentLayer,currentLine);
//        }
//        else if(isSettingRow(code)){
//            updateVariablesValues(code,currentLayer);
//        }
//        else{
//            throw new InvalidFormatException();
//        }
//    }

    // this function extract string after a given word
    private String extractStringAfterWord(String code, String word) {
        int index = code.indexOf(word);
        if (index == -1) return code; // Return the original string if the word is not found
        return code.substring(index + word.length()).trim();
    }

    // this function checks is a code start with a certain pattern
    private boolean startsWithPattern(String code, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    //-----------------------Final-------------------------
    /**
     * this function checks is the format of a code that starts with final is
     * valid
     * @param code the code that starts with final
     * @return returns true if final and correct false if not final
     * and error if is final and incorrect format
     */
    public boolean isCorrectFormatFinal(String code) {
        if (startsWithPattern(code, STARTS_WITH_FINAL)) {
            String secondPartOfCode = extractStringAfterWord(code, "final");
            Pattern pattern = Pattern.compile(MIXED);
            Matcher matcher = pattern.matcher(secondPartOfCode);
            if(!matcher.matches()){
                throw new InvalidFormatException();
            }
            pattern = Pattern.compile(INITIALIZED_ONLY);
            matcher = pattern.matcher(secondPartOfCode);
            if(!matcher.matches()){
                throw new InvalidFinalVariableInitializationException();
            }
            return true;
        }
        return false;
    }

    //-------------------definition or initialization of variables------

    /**
     * this function checks is the format of initialization is mixed
     * so every variable can be initialized or not
     * @param code the code we checked
     * @return if it initialization and correct return true if not initialization return false
     * if initialization that incorrect throw exception
     */
    public boolean isMixed(String code) {
        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
            Pattern patternMixed = Pattern.compile(MIXED);
            Matcher matcher = patternMixed.matcher(code);
            if(!matcher.matches()){
                throw new InvalidFormatException();
            }
            return true;
        }
        return false;
    }

    /**
     * this function checks is a set row is valid
     * @param code the code we check is the set row valid
     * @return if set row valid return true else throw exception
     */
    public boolean isSettingRow(String code) {
        Pattern pattern = Pattern.compile(SETTING_ROW);
        Matcher matcher = pattern.matcher(code);
        if(!matcher.matches()){
            throw new InvalidFormatException();
        }
        else{
            return true;
        }
    }

//    private boolean isInitializationOnly(String code) {
//        if (startsWithPattern(code, STARTS_WITH_VAR_TYPE)) {
//            Pattern patternMixed = Pattern.compile(INITIALIZED_ONLY);
//            Matcher matcher = patternMixed.matcher(code);
//            return matcher.matches();
//        }
//        return false;
//    }
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

    private void extractDataFinal(String code, int currentLayer, int currentLine) {
        extractNewVarsFromRow(code,true,1, currentLayer, currentLine);
    }
    // -------------------extract data mixed -----------------------------
    private void extractDataMixed(String code, int currentLayer, int currentLine) {
        extractNewVarsFromRow(code,false,0,currentLayer,currentLine);
    }


    // ------------------------- update variables -------------------------------
    private void updateVariablesValues(String code, int currentLayer) {
        Pattern pattern = Pattern.compile(EXTRACT_DATA_MIXED);
        Matcher matcher = pattern.matcher(code);
        ArrayList<String> subStrings = new ArrayList<>();
        while (matcher.find()) {
            subStrings.add(matcher.group());
        }
        for (int i = 0; i < subStrings.size(); i++) {
            updateVar(subStrings.get(i), currentLayer);
        }
    }

    private void updateVar( String code, int layer) {
        code = code.trim();
        if (code.contains("=")) {
            Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*"+INPUT_REGEX+"\\s*");
            Matcher matcher = pattern.matcher(code);
            if (matcher.matches()) {
                String varName = matcher.group(1).trim(); // Captures the variable name
                // Captures the value, preserving formatting
                String value = matcher.group(2).trim();
                Variable variable = this.variableDataBase.findVarByNameOnly(varName, layer);
                if (variable == null) {
                    throw new UnreachableVariable(varName);
                }
                System.out.println("VARNAME: " + varName + " ,VALUE: " + value);
                variable.setValue(value);
            }
        } else {
            String varName = code;
            Variable variable = this.variableDataBase.findVarByNameOnly(varName, layer);
            if(variable == null){
                throw new UnreachableVariable(varName);
            }
            else{
                throw new InvalidFormatException();
            }
        }
    }

    //--------------------extract data from string -----------------
    private void extractNewVarsFromRow(String code,
                                       boolean isFinal,
                                       int typeWordIndex,
                                       int currentLayer,
                                       int currentLine) {
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
        addNewVars(typeWord,subStrings,currentLayer,isFinal,this.variableDataBase);
    }

    private void addNewVars(String typeStr,
                                            ArrayList<String> subStrings,
                                            int layer, boolean isFinal,
                                            VariableDataBase variableDataBase){
        System.out.println(subStrings);
        VariableType type = VariableType.fromString(typeStr);
        for (int i = 1; i < subStrings.size(); i++) {
            createVariable(type, subStrings.get(i), layer, isFinal,variableDataBase);
        }
    }


    private void createVariable(VariableType type,
                                String code,
                                int layer,
                                boolean isFinal,
                                VariableDataBase variableDataBase) {
        code = code.trim();
        if (code.contains("=")) {
            String[] subWords = code.split("=");
            String varName = subWords[0].trim();
            String value = subWords[1].trim();
            Variable other= this.variableDataBase.findVarByNameOnly(varName,layer);
            if(other!=null){
                if(other.getLayer()==layer){
                    throw new DoubleCreatingException(varName);
                }
            }
            Variable variable = new Variable(varName,layer,
                    isFinal,true,type, value,this.variableDataBase);
            variableDataBase.addVariable(variable);
        } else {
            String varName = code;
            Variable variable = new Variable(varName,layer,isFinal,type,this.variableDataBase);
            variableDataBase.addVariable(variable);
        }
    }
    // main closed
//    public static void main(String[] args) {
//        String code1 = "double x=2.0,b=5;";
//        String code2 = "void foo(int a){";
//        VariableDataBase variableDataBase1 = new VariableDataBase();
//        RowProcessing rowProcessing1 = new RowProcessing( variableDataBase1);
//        rowProcessing1.processCode(code1,14,23);
//        rowProcessing1.processCode(code2,14,24);
//        System.out.println(variableDataBase1);
//
//    }
}
