package com.niujunjie.www.framapp.utils;

import android.util.Log;

public class TLog {
	public static boolean DEBUG = true;

	public TLog() {
	}

	public static final void d(String LOG_TAG,String log) {
		if (DEBUG)
			Log.d(LOG_TAG, log);
	}

	public static final void e(String LOG_TAG,String log) {
		if (DEBUG)
			Log.e(LOG_TAG, "" + log);
	}

	public static final void i(String LOG_TAG,String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
	}

	public static final void log(String LOG_TAG, String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
	}

	public static final void v(String LOG_TAG,String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
	}

	public static final void w(String LOG_TAG,String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}
}
