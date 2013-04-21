package ru.ezhoff.geolocation;

import android.util.Log;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 21.04.13
 */
public class Logger {

    private String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void debug(String msg) {
        Log.d(tag, msg);
    }

    public void debug(String msg, Throwable tr) {
        Log.d(tag, msg, tr);
    }

    public void error(String msg) {
        Log.e(tag, msg);
    }

    public void error(String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public void info(String msg) {
        Log.i(tag, msg);
    }

    public void info(String msg, Throwable tr) {
        Log.i(tag, msg, tr);
    }

    public void warn(String msg) {
        Log.w(tag, msg);
    }

    public void warn(String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }
}
