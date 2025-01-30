package main.exceptions_main;

import java.io.IOException;

/**
 * We raise it in the case the provided code file
 * was not of sjava format
 */
public class IllegalFileFormatException extends IOException {
    private static final String MESSAGE ="Wrong file format: expected .sjava file.";
    /**
     * IllegalFileFormatException constructor
     */
    public IllegalFileFormatException() {
        super(MESSAGE);
    }
}
