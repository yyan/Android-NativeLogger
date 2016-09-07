package cn.jesse.nativelogger.logger;

import android.util.Log;

import cn.jesse.nativelogger.logger.base.AbstractLogger;

/**
 * Created by jesse on 9/6/16.
 */
public class AndroidLogger extends AbstractLogger {

    public AndroidLogger(String tag) {
        super(tag);
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(String subTag, String msg) {

    }

    @Override
    public void debug(String subTag, String format, Object arg) {

    }

    @Override
    public void debug(String subTag, String format, Object argA, Object argB) {

    }

    @Override
    public void debug(String subTag, String format, Object... arguments) {

    }

    @Override
    public void debug(String subTag, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void info(String subTag, String msg) {

    }

    @Override
    public void info(String subTag, String format, Object arg) {

    }

    @Override
    public void info(String subTag, String format, Object argA, Object argB) {

    }

    @Override
    public void info(String subTag, String format, Object... arguments) {

    }

    @Override
    public void info(String subTag, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {

    }

    @Override
    public void warn(String subTag, String msg) {

    }

    @Override
    public void warn(String subTag, String format, Object arg) {

    }

    @Override
    public void warn(String subTag, String format, Object... arguments) {

    }

    @Override
    public void warn(String subTag, String format, Object argA, Object argB) {

    }

    @Override
    public void warn(String subTag, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void error(String subTag, String msg) {

    }

    @Override
    public void error(String subTag, String format, Object arg) {

    }

    @Override
    public void error(String subTag, String format, Object argA, Object argB) {

    }

    @Override
    public void error(String subTag, String format, Object... arguments) {

    }

    @Override
    public void error(String subTag, Throwable t) {

    }
}
