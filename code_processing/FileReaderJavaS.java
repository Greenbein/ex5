package code_processing;

import code_processing.exceptions.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReaderJavaS {
    public int readAndCheckBasicErrorFile(String inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))){
            String line;
            int lineNumber = 0;
            int layer = 0;
            while ((line = bufferedReader.readLine()) != null) {
                // if line starts with single line comment or blank continue
                // to the next line in the file
                if(line.isBlank()||line.startsWith("//")){
                    lineNumber++;
                    continue;
                }
                invalidEndingRow(line,lineNumber);
                invalidComments(line,lineNumber);
                invalidArray(line,lineNumber);
                lineNumber++;
            }
        }
        catch (IOException e) {
               return 2;
        }
        catch (InvalidMultiLineCommentException|JavaDocException
                |InvalidSingleCommentLineException|InvalidEndingForSentenceException e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    /**
     * this function checks is there invalid comment if there are throw exception
     * @param line the line we check is there exception
     * @param lineNumber the line number in the file
     */
    public void invalidComments(String line, int lineNumber){
        if(line.contains("/*")){
            throw new InvalidMultiLineCommentException(lineNumber);
        }
        if(line.contains("/**")){
            throw new JavaDocException(lineNumber);
        }
        if(line.contains("//")){
            throw new InvalidSingleCommentLineException(lineNumber);
        }
    }

    /**
     * this function checks is the line ends with ';' , '{' or '}' if not throw exception
     * @param line the line we check how it ends
     * @param lineNumber the line number in the file
     */
    public void invalidEndingRow(String line, int lineNumber){
        String stripedLine = line.strip();
        if(stripedLine.endsWith(";")||stripedLine.endsWith("{") ||stripedLine.endsWith("}")){
            return;
        }
        throw new InvalidEndingForSentenceException(lineNumber);
    }

    /**
     * this function checks is there array if there is throw exception
     * @param line the exact line we check
     * @param lineNumber the line number in the file
     */
    public void invalidArray(String line, int lineNumber){
        String finalArray = "\\s*final\\s+(int|boolean|char|String|double)\\s*\\[].*";
        Pattern finalPattern = Pattern.compile(finalArray);
        Matcher finalMatcher = finalPattern.matcher(line);
        String notFinalArray = "\\s*(int|boolean|char|String|double)\\s*\\[].*";
        Pattern notFinalPattern = Pattern.compile(notFinalArray);
        Matcher notFinalMatcher = notFinalPattern.matcher(line);
        if(finalMatcher.matches()||notFinalMatcher.matches()){
            throw new InvalidArrayException(lineNumber);
        }


        //throw new InvalidArrayException(lineNumber); -----aaaaaaaaa excception---
    }
}
