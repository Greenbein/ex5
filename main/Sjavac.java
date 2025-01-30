package main;

import code_processing.FileReaderJavaS;
import databases.VariableDataBase;
import exceptions.exceptions_main.IllegalFileFormatException;
import exceptions.exceptions_main.IllegalNumberOfArguments;

import java.io.IOException;

/**
 * this class would get the file path from the user and would process it
 * and print 0 if it's valid, would print 2 if there is IOException
 * else would print 1 for any other exception
 */
public class Sjavac {
    private static final String SJAVA_FORMAT = ".sjava";
    /**
     * main func for Sjavac
     * @param args the argument we would get form user (path to file)
     */
    public static void main(String[] args) {
        try{
            // validate we got one argument
            if (args.length != 1) {
                throw new IllegalNumberOfArguments();
            }
            // validate ends with .sjava
            String fileName = args[0];
            if (!fileName.endsWith(SJAVA_FORMAT)) {
                throw new IllegalFileFormatException();
            }
            // create new variable database
            VariableDataBase variableDataBase = new VariableDataBase();
            //checks the file
            FileReaderJavaS fileReaderJavaS = new FileReaderJavaS(variableDataBase);
            // state of file (0,1,2 like we have explained before)
            int stateOfCheck = fileReaderJavaS.readAndCheckBasicErrorFile(fileName);
            if(stateOfCheck==2|stateOfCheck==1){
                System.out.println(stateOfCheck);
                return;
            }
            int stateIfSecondCheck = fileReaderJavaS.functionsCheckInFile(fileName);
            System.out.println(stateIfSecondCheck);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.out.println(2);
        }
    }
}
