package com.teammetallurgy.metallurgycm.handler;

import org.apache.logging.log4j.Logger;

public class LogHandler
{
    private static Logger log;

    public static void setLogger(Logger logger)
    {
        log = logger;
    }

    public static void info(String message)
    {
        log.info(message);
    }

    public static void trace(String message)
    {
        log.trace(message);
    }

    public static void warning(String message)
    {
        log.warn(message);
    }

    public static void abstractorVerboseLog(String message)
    {
        if (ConfigHandler.abstractorGenVerboseLog)
        {
            log.trace(message);
        }
    }

    public static void abstractorInvaildLog(String message)
    {
        if (ConfigHandler.abstractorInvaildLog)
        {
            log.warn(message);
        }
    }
}
