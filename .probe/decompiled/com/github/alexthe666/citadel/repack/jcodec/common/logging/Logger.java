package com.github.alexthe666.citadel.repack.jcodec.common.logging;

import java.util.LinkedList;
import java.util.List;

public class Logger {

    private static List<LogSink> stageSinks = new LinkedList();

    private static List<LogSink> sinks;

    private static LogLevel globalLogLevel = LogLevel.INFO;

    public static void debug(String message) {
        message(LogLevel.DEBUG, message, null);
    }

    public static void debug(String message, Object... args) {
        message(LogLevel.DEBUG, message, args);
    }

    public static void info(String message) {
        message(LogLevel.INFO, message, null);
    }

    public static void info(String message, Object... args) {
        message(LogLevel.INFO, message, args);
    }

    public static void warn(String message) {
        message(LogLevel.WARN, message, null);
    }

    public static void warn(String message, Object... args) {
        message(LogLevel.WARN, message, args);
    }

    public static void error(String message) {
        message(LogLevel.ERROR, message, null);
    }

    public static void error(String message, Object... args) {
        message(LogLevel.ERROR, message, args);
    }

    private static void message(LogLevel level, String message, Object[] args) {
        if (globalLogLevel.ordinal() < level.ordinal()) {
            if (sinks == null) {
                synchronized (Logger.class) {
                    if (sinks == null) {
                        sinks = stageSinks;
                        stageSinks = null;
                        if (sinks.isEmpty()) {
                            sinks.add(OutLogSink.createOutLogSink());
                        }
                    }
                }
            }
            Message msg;
            if (LogLevel.DEBUG.equals(globalLogLevel)) {
                StackTraceElement tr = Thread.currentThread().getStackTrace()[3];
                msg = new Message(level, tr.getFileName(), tr.getClassName(), tr.getMethodName(), tr.getLineNumber(), message, args);
            } else {
                msg = new Message(level, "", "", "", 0, message, args);
            }
            for (LogSink logSink : sinks) {
                logSink.postMessage(msg);
            }
        }
    }

    public static synchronized void setLevel(LogLevel level) {
        globalLogLevel = level;
    }

    public static synchronized LogLevel getLevel() {
        return globalLogLevel;
    }

    public static void addSink(LogSink sink) {
        if (stageSinks == null) {
            throw new IllegalStateException("Logger already started");
        } else {
            stageSinks.add(sink);
        }
    }
}