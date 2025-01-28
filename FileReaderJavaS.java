import row_processing.exceptions.InvalidEndingForSentenceException;
import row_processing.exceptions.InvalidMultiLineCommentException;
import row_processing.exceptions.InvalidSingleCommentLineException;
import row_processing.exceptions.JavaDocException;

import java.io.*;

public class FileReaderJavaS {
    public int readAndCheckBasicErrorFile(String inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))){
            String line;
            int lineNumber = 1;
            while ((line = bufferedReader.readLine()) != null) {
                // if line starts with single line comment return
                if(line.startsWith("//")){
                    continue;
                }
                if(line.contains("/*")){
                    throw new InvalidMultiLineCommentException(lineNumber);
                }
                // javadoc comment is invalid
                if(line.contains("/**")){
                    throw new JavaDocException(lineNumber);
                }
                // single line comments not in the start of the sentence invalid
                if(line.contains("//")){
                    throw new InvalidSingleCommentLineException(lineNumber);
                }
                // sentence must end with ';' , '{' or '}'
                if(!(line.strip().endsWith(";")||line.strip().endsWith("{")
                        ||line.strip().endsWith("}"))){
                    throw new InvalidEndingForSentenceException(lineNumber);
                }
                lineNumber++;
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }
}
