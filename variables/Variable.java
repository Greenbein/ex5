package variables;

import variables.basic_exceptions.*;
import variables.boolean_s_java.exceptions.InvalidBooleanException;
import variables.char_s_java.exceptions.InvalidCharException;
import variables.integer_s_java.exceptions.InvalidIntegerInputException;
import variables.string_s_java.exceptions.InvalidStringInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum VariableType{
    INTEGER, DOUBLE, BOOLEAN, STRING, CHAR
}
/**
 * This class implements java variable
 */
public class Variable {
    // relevant for boolean case
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private String valueType;
    // until here
    private String name;
    private int layer;
    private boolean isFinal;
    private boolean isInitialized;
    private VariableType type;
    private Object value;
    /**
     * default constructor of variable
     * @param name name of variable
     * @param layer layer of variable
     * @param isFinal is variable final
     * @param isInitialized is variable initialized
     */
    public Variable(String name,
                       int layer,
                       boolean isFinal,
                       boolean isInitialized,
                       String value) {
        if(!isInitialized && isFinal) {
            throw new InvalidFinalVariableIntialization();
        }
        if(isValidName(name)) {
            this.name = name;
        }
        this.layer = layer;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }

    /**
     * this function checks is the input we got in the
     * variable is valid if not throw relevant exception
     * @param input the input of the variable
     * @return is the input valid or not
     */
    public boolean isValidInput(String input, VariableType type){
        switch(type){
            case INTEGER:
                return isValidInputForInteger(input);
            case DOUBLE:
                return isValidInputForDouble(input);
            case BOOLEAN:
                return isValidInputForBoolean(input);
            case STRING:
                return isValidInputForString(input);
            case CHAR:
                return isValidInputForChar(input);
        }
        return false;
    }

    /**
     * this function sets
     * the value of the certain variable
     * @param input the value we need to set
     */
    public  void setValue(String input){
        switch(type){
            case INTEGER:
                setValueBoolean(input);
                break;
            case DOUBLE:
                setValueDouble(input);
                break;
            case BOOLEAN:
                setValueDouble(input);
                break;
            case STRING:
                stringSetValue(input);
                break;
            case CHAR:
                setValueChar(input);
        }
    }

    /**
     * this function initializes
     * the value of the certain variable
     * @param input the value we need to implement in the initializes variable
     */
    public  void initializeValue(String input){
        switch(type){
            case INTEGER:
               initializeValueInteger(input);
                break;
            case DOUBLE:
                initializeValueDouble(input);
                break;
            case BOOLEAN:
                initializeValueBoolean(input);
                break;
            case STRING:
                initializeValueString(input);
                break;
            case CHAR:
                initializeValueChar(input);
        }
    }

    // this function checks is a name of a variable is valid
    // if not throwing relevant exception
    private boolean isValidName(String name){
        if(name.equals("_")){
            // exception name is only _
            throw new NameStartsWithNumberException();
        }
        if(name.matches("^__.*")){
            // exception starts with double __
            throw new NameStartsWithDoubleUnderscoreException();
        }
        if(name.matches("^\\d{1}.*")){
            // exception starts with a number
            throw new NameStartsWithNumberException();
        }
       if(!name.matches("^\\w+$")){
           // exception illegal format using invalid format
           throw new InvalidFormatName();
       }
       return true;
    }

    // --------- Boolean ---------------------------------------

    /**
     * this function returns is the string represents an input of a boolean
     * @param input the input of the variable
     * @return  is the input represents an input of a boolean
     */
    public boolean isValidInputForBoolean(String input) {
        if(isValidInputForInteger(input)){
            this.valueType = INTEGER;
            return true;
        }
        if(isValidInputForDouble(input)){
            this.valueType = DOUBLE;
            return true;
        }
        return checkIsInputBoolean(input);
    }

    /**
     * this function set the value of a boolean variable
     * @param input the value we need to set
     */
    public void setValueBoolean(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        updateValue(input);
    }

    /**
     * this function initializes the value of a boolean variable
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValueBoolean(String input) {
        updateValue(input);
    }

    // checks is String input represents a boolean
    private boolean checkIsInputBoolean(String input){
        Pattern truePattern = Pattern.compile("^true$");
        Matcher m = truePattern.matcher(input);
        if(m.matches()){
            this.valueType = TRUE;
            return true;
        }
        Pattern falsePattern = Pattern.compile("^false$");
        m = falsePattern.matcher(input);
        if(m.matches()){
            this.valueType = FALSE;
            return true;
        }
        return false;
    }

    // switches a string that represents integer into a boolean
    private void switchIntegerToBoolean(String input){
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        int boolIntValue = Integer.parseInt(cleanedInput);
        if(boolIntValue != 0 ){
            this.value = true;
        }
        else{
            this.value = false;
        }
    }

    // switches a string that represents double into a boolean
    private void switchDoubleToBoolean(String input){
        String cleanedInput = input.replaceFirst
                ("^[+]", "").replaceFirst("^0+", "");
        double boolDoubleValue = Double.parseDouble(cleanedInput);
        if(boolDoubleValue != 0 ){
            this.value = true;
        }
        else {
            this.value = false;
        }
    }

    // switches a string that represents boolean into a boolean
    private void switchBooleanToBoolean(String input){
        if(input.equals(TRUE)){
            this.value = true;
        }
        else if(input.equals(FALSE)){
            this.value = false;
        }
    }

    // this function updates the value of boolean variable
    private void updateValue(String input){
        if(isValidInputForBoolean(input)){
            switch(this.valueType){
                case INTEGER:
                    switchIntegerToBoolean(input);
                    break;
                case DOUBLE:
                    switchDoubleToBoolean(input);
                    break;
                default:
                    switchBooleanToBoolean(input);
                    break;
            }
        }
        else{
            throw new InvalidBooleanException();
        }
    }

    // ----------- Char ------------------------------------------

    /**
     * this function checks is the input for the variable is valid
     * @param input the input of the variable
     * @return is the input valid or not
     */
    public boolean isValidInputForChar(String input) {
        Pattern p = Pattern.compile("^[^\\\\'\",]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * this function sets the value of variable char
     * @param input the value we need to set
     */
    public void setValueChar(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInputForChar(input)){
            this.value = input.charAt(0);
        }
        else{
            throw new InvalidCharException(this.getName());
        }
    }

    /**
     * this function adding the value of variable char into the variable in initialization
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValueChar(String input) {
        if(isValidInputForChar(input)){
            this.value = input.charAt(0);
        }
        else{
            throw new InvalidCharException(this.getName());
        }
    }

    // ----------- Double ------------------------------------------

    /**
     * this function checks is the given input for the variable is valid
     * @param input the input of the variable
     * @return is the input valid or not
     */
    public boolean isValidInputForDouble(String input) {
        Pattern pDouble = Pattern.compile("^[+-]?(\\d*.\\d+|^[+-]?\\d+.\\d*)$");
        Pattern pInteger = Pattern.compile("^[+-]?\\d+$");
        Matcher mInteger = pInteger.matcher(input);
        if(mInteger.matches()) {
            return true;
        }
        Matcher mDouble = pDouble.matcher(input);
        return mDouble.matches();
    }

    /**
     * this function sets the value of double variable
     * @param input the value we need to set
     */
    public void setValueDouble(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInputForDouble(input)){
            String cleanedInput = input.replaceFirst
                    ("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    /**
     * this function initializes the value of double variable
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValueDouble(String input) {
        if(isValidInputForDouble(input)){
            String cleanedInput = input.replaceFirst
                    ("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    // ----------- Integer ------------------------------------------

    /**
     * check whether the input is valid for integerSJava variable
     * @param input the input of the variable
     * @return true or false
     */
    public boolean isValidInputForInteger(String input) {
        Pattern p = Pattern.compile("^[+-]?\\d+$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Initialize integerSJava
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValueInteger(String input) {
        if(this.isValidInputForInteger(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    /**
     * Set new value to integerSJava variable
     * @param input the value we need to set
     */
    public void setValueInteger(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInputForInteger(input)){
            String cleanedInput = input.replaceFirst("^[+]", "").replaceFirst("^0+", "");
            this.value = Integer.parseInt(cleanedInput);
        }
        else{
            throw new InvalidIntegerInputException(this.getName());
        }
    }

    // ----------- String ------------------------------------------

    /**
     * checks is the input of the variable valid
     * @param input the input of the variable
     * @return is the input is valid
     */
    public boolean isValidInputForString(String input) {
        Pattern p = Pattern.compile("^.*[\\\\'\",].*$");
        Matcher m = p.matcher(input);
        return !m.matches();
    }

    /**
     * this function initializes String variable
     * @param input the value we need to implement in the initializes variable
     */
    public void initializeValueString(String input) {
        if(this.isValidInputForString(input)){
            this.value = input;
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    /**
     * this function set value for a variable of type
     * String
     * @param input the value we need to set
     */
    public void stringSetValue(String input) {
        if(this.isFinal()){
            throw new InvalidSetFinalVariableException();
        }
        if(isValidInputForString(input)){
            this.value = input;
        }
        else{
            throw new InvalidStringInputException(this.getName());
        }
    }

    // ---------------- getters for variable fields-------------------------

    // return is a variable is final
    public boolean isFinal() {
        return this.isFinal;
    }

    // return is a variable initialized
    public boolean isInitialized() {
        return this.isInitialized;
    }

    // returns the name of the variable
    public String getName() {
        return this.name;
    }

    // returns the layer of the object
    public int getLayer() {
        return this.layer;
    }

}
