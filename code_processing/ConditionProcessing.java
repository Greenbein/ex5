package code_processing;

import code_processing.condition_exceptions.InvalidFormatForIfCommandException;
import code_processing.condition_exceptions.InvalidFormatForWhileCommandException;
import code_processing.condition_exceptions.InvalidVarTypeForConditionException;
import databases.VariableDataBase;
import valid_name.ValidName;
import variables.Variable;
import variables.VariableType;
import variables.exceptions.UnreachableVariable;
import variables.variable_managers.CharManager;
import variables.variable_managers.StringManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionProcessing {
    private static final String INPUT_FOR_CONDITION =
            "\\s*([+-]?(\\d*(d*\\.\\d*)?)|\\w+|true|false|\".*\"|\'.*\')\\s*";
    private static final String CORRECT_CONDITION_FORMAT= "\\(("+INPUT_FOR_CONDITION+"(\\s*(\\&\\&|\\|\\|)\\s*)"+INPUT_FOR_CONDITION+")*"+INPUT_FOR_CONDITION+"\\s*\\)";
    private static final String STARTS_WITH_IF = "^\\s*if\\s.*";
    private static final String STARTS_WITH_WHILE = "^\\s*while\\s.*";
    private final VariableDataBase db;
    public ConditionProcessing(VariableDataBase db) {
        this.db = db;
    }


    public boolean isCorrectWhileFormat(String code, int layer) {
        String regexWhile = "\\s*while\\s*"+CORRECT_CONDITION_FORMAT+"\\s*\\{";
        if(code.matches(regexWhile)){
            checkConditionParameters(code,layer);
        }
        else{
            throw new InvalidFormatForWhileCommandException();
        }
        return false;
    }

    private boolean isCorrectIfFormat(String code, int layer) {
        String regexIf = "\\s*if\\s*"+CORRECT_CONDITION_FORMAT+"\\s*\\{";
        if(code.matches(regexIf)){
            checkConditionParameters(code,layer);
        }
        else{
            throw new InvalidFormatForIfCommandException();
        }
        return false;
    }

    private boolean checkConditionParameters(String code, int layer) {
        System.out.println("Code :"+code);
        Pattern pattern = Pattern.compile("\\((.*?)\\)"); // Capture text inside ( )
        Matcher matcher = pattern.matcher(code);
        if(matcher.find()){
            String parameters = matcher.group(1);
            String[] parts = parameters.split("\\|\\||&&");
            for(String part : parts){
                checkParameterValidity(part,layer);
            }
        }
        return true;
    }

    private boolean checkParameterValidity(String parameter, int layer) {
        if(ValidName.isValidVarNameInput(parameter)){
            Variable variable = db.findVarByNameOnly(parameter,layer);
            if(variable != null){
                if (variable.getValueType().equals(VariableType.STRING)||variable.getValueType().equals(VariableType.CHAR)){
                    throw new InvalidVarTypeForConditionException(variable.getName(),variable.getValueType());
                }
                return true;
            }
            else{
                throw new UnreachableVariable(parameter);
            }
        }
        else{
            StringManager stringManager = new StringManager();
            if(stringManager.isValidInput(parameter)){
                throw new InvalidVarTypeForConditionException(parameter,VariableType.STRING);
            }
            CharManager charManager = new CharManager();
            if(charManager.isValidInput(parameter)){
                throw new InvalidVarTypeForConditionException(parameter,VariableType.CHAR);
            }
            return true;
        }
    }

    public boolean startsWithIf(String code){
        Pattern pattern = Pattern.compile(STARTS_WITH_IF);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }
    public boolean startsWithWhile(String code){
        Pattern pattern = Pattern.compile(STARTS_WITH_WHILE);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    public static void main(String[] args) {
        VariableDataBase db = new VariableDataBase();
        ConditionProcessing process = new ConditionProcessing(db);
        RowProcessing row = new RowProcessing(db);
        String code1 = "boolean x=2,b=0;";
        String code2 = "char mystring=\'a\';";
        row.processCode(code1,1,1);
        row.processCode(code2,1,1);
        String code = "while(x||b){";
        process.isCorrectWhileFormat(code,22);
    }


}
