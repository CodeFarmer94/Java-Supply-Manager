package com.loggers;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenJPALoggingExample {

    public static void enableOpenJPATraceLogging() {
        // Get the logger for OpenJPA
        Logger openJPALogger = Logger.getLogger("openjpa");

        // Set the log level to TRACE
        openJPALogger.setLevel(Level.FINEST);

        // Add a console handler to redirect logs to the console
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        openJPALogger.addHandler(consoleHandler);
    }

   
}
