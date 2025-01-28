package code_processing;

import code_processing.exceptions.InvalidEndingForSentenceException;
import code_processing.exceptions.InvalidMultiLineCommentException;
import code_processing.exceptions.InvalidSingleCommentLineException;
import code_processing.exceptions.JavaDocException;

import java.io.*;

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
}
