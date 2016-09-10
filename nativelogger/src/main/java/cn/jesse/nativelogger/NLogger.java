package cn.jesse.nativelogger;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.logging.Formatter;

import cn.jesse.nativelogger.formatter.SimpleFormatter;
import cn.jesse.nativelogger.logger.AndroidLogger;
import cn.jesse.nativelogger.logger.FileLogger;
import cn.jesse.nativelogger.logger.LoggerLevel;
import cn.jesse.nativelogger.logger.base.IFileLogger;
import cn.jesse.nativelogger.logger.base.ILogger;
import cn.jesse.nativelogger.util.CrashWatcher;

/**
 * Created by jesse on 9/5/16.
 */
public class NLogger extends AbstractNativeLogger{
    private static final String TAG = NLogger.class.getSimpleName();
    private static NLogger mInstance;
    private static Builder builder;

    private volatile ILogger defaultLogger = new AndroidLogger(TAG);
    private volatile ILogger fileLogger;

    private NLogger() {

    }

    /**
     * get instance
     * @return
     */
    public static NLogger getInstance() {
        if (mInstance == null) {
            synchronized (NLogger.class) {
                if (mInstance == null) {
                    mInstance = new NLogger();
                }
            }
        }
        return mInstance;
    }

    /**
     * build builder to android logger & file logger & exception watcher
     * @param builder
     * @return
     */
    private synchronized NLogger build(Builder builder) {
        if (builder.isCatchException) {
            CrashWatcher.getInstance().init();
            CrashWatcher.getInstance().setListener(builder.uncaughtExceptionListener);
        } else
            CrashWatcher.getInstance().setListener(null);


        defaultLogger.setTag(builder.tag);
        defaultLogger.setLevel(builder.loggerLevel);

        if (!builder.isFileLoggerEnable) {
            fileLogger = null;
            return mInstance;
        }

        if (fileLogger == null) {
            fileLogger = new FileLogger(builder.tag);


        } else {
            fileLogger.setTag(builder.tag);
        }

        fileLogger.setLevel(builder.loggerLevel);
        IFileLogger iFileLogger = (IFileLogger) fileLogger;
        iFileLogger.setFilePathAndFormatter(builder.fileDirectory, builder.fileFormatter, builder.expiredPeriod);

        return mInstance;
    }

    /**
     * instance builder
     * @return
     */
    public Builder builder() {
        if (builder == null) {
            synchronized (NLogger.class) {
                if (builder == null) {
                    builder = new Builder();
                }
            }
        }
        return builder;
    }

    /**
     * get android logger
     *
     */
    @Override
    ILogger getDefaultLogger() {
        return this.defaultLogger;
    }

    /**
     * get file logger
     *
     */
    @Override
    ILogger getFileLogger() {
        return this.fileLogger;
    }

    public static final class Builder {
        private String tag = NLogger.class.getSimpleName();
        private LoggerLevel loggerLevel = LoggerLevel.WARN;
        private boolean isCatchException = false;
        private CrashWatcher.UncaughtExceptionListener uncaughtExceptionListener;
        private boolean isFileLoggerEnable = true;
        private String fileDirectory = Environment.getExternalStorageDirectory().getPath() + "/native.logs/";
        private Formatter fileFormatter = new SimpleFormatter();
        private int expiredPeriod = 1;

        /**
         * init builder , prepare env
         *
         */
        public Builder() {
            File filePath = new File(fileDirectory);
            if (!filePath.exists())
                filePath.mkdirs();

            uncaughtExceptionListener = new CrashWatcher.UncaughtExceptionListener() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            };
        }

        /**
         * set tag
         *
         * @throws IllegalArgumentException if the tag is null | empty
         */
        public Builder tag(String tag) {
            if (TextUtils.isEmpty(tag))
                throw new IllegalArgumentException("unexpected tag");

            this.tag = tag;
            return this;
        }

        /**
         * catch uncaught exception
         *
         */
        public Builder catchException(boolean enable, CrashWatcher.UncaughtExceptionListener listener) {
            this.isCatchException = enable;
            this.uncaughtExceptionListener = listener;
            return this;
        }

        /**
         * set the logger level
         *
         */
        public Builder loggerLevel(LoggerLevel level) {
            this.loggerLevel = level;
            return this;
        }

        /**
         * set file logger enable
         *
         */
        public Builder fileLogger(boolean enable) {
            this.isFileLoggerEnable = enable;
            return this;
        }

        /**
         * set pack log period
         *
         * @throws IllegalArgumentException if the path is empty
         */
        public Builder fileDirectory(String path) {
            if (TextUtils.isEmpty(path))
                throw new IllegalArgumentException("unexpected path");

            File filePath = new File(path);
            if (!filePath.exists())
                if (!filePath.mkdirs())
                    throw new IllegalArgumentException("unexpected path");

            this.fileDirectory = path;
            return this;
        }

        /**
         * set file logger formatter
         *
         * @throws IllegalArgumentException if the formatter is null
         */
        public Builder fileFormatter(Formatter formatter) {
            if (null == formatter)
                throw new IllegalArgumentException("unexpected file formatter");

            this.fileFormatter = formatter;
            return this;
        }

        /**
         * set the period of file expired
         *
         * @throws IllegalArgumentException if the period <= 0
         */
        public Builder expiredPeriod(int period) {
            if (period <= 0)
                throw new IllegalArgumentException("unexpected period : " + period);

            expiredPeriod = period;
            return this;
        }

        public NLogger build() {
            return mInstance.build(this);
        }
    }
}
