package com.ideas2it.censusMigration.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLogger {
    private final Logger logger;

    public CustomLogger(Class<?> className) {
        logger = LogManager.getLogger(className);
    }

    /**
     * <h1>
     *     INFO
     * </h1>
     * <p>
     *     Logs the information message.
     * </p>
     *
     * @param message - Holds the info message
     */
    public void info(String message){
        logger.info(message);
    }

    /**
     * <h1>
     *     WARN
     * </h1>
     * <p>
     *     Logs the warning message.
     * </p>
     *
     * @param message - Holds the warning message
     */
    public void warn(String message){
        logger.warn(message);
    }

    /**
     * <h1>
     *     ERROR
     * </h1>
     * <p>
     *     Logs the error message.
     * </p>
     *
     * @param message - Holds the error message
     */
    public void error(String message){
        logger.error(message);
    }
}