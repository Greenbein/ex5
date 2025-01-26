import databases.VariableDataBase;
import variables.exceptions.JavaDocException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowProcessing {
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
       // String[] row = text.split("\s");
        checkSlash(text, currentLayer);



    }
    private void checkRow(){
        checkSlash(text, currentLayer);
    }
    private void checkSlash(String text, int lineNumber) {
        if(text.startsWith("//")){
            return;
        }
        // /* */ /**
        if(text.contains("/")){
            throw new JavaDocException(lineNumber);
        }
    }


}
