package com.github.zeromodsinc.cyanide.ui.playerview.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum LogHelper {
    INSTANCE;

    private Logger logger;

    public static void info(String format, Object... args) {
        INSTANCE.log(Level.INFO, format, args);
    }

    public static void log(Level level, Throwable exception, String format, Object... args) {
        if (args != null && args.length > 0)
            INSTANCE.getLogger().log(level, String.format(format, args), exception);
        else
            INSTANCE.getLogger().log(level, format, exception);
    }

    public static void severe(String format, Object... args) {
        INSTANCE.log(Level.ERROR, format, args);
    }

    public static void warning(String format, Object... args) {
        INSTANCE.log(Level.WARN, format, args);
    }

    public Logger getLogger() {
        if (logger == null)
            init();

        return logger;
    }

    private void init() {
        if (logger != null)
            return;

        logger = LogManager.getLogger("ExtImpl/MenuMobs");
    }

    private void log(Level level, String format, Object... data) {
        if (data != null && data.length > 0)
            getLogger().log(level, String.format(format, data));
        else
            getLogger().log(level, format);
    }
}
