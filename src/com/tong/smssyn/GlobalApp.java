package com.tong.smssyn;

import android.app.Application;
import android.util.Log;

public class GlobalApp extends Application {

	private static final String TAG = GlobalApp.class.getSimpleName();
	private static GlobalApp mGlobalApp = null;

	@Override
	public void onCreate() {
		Log.d(TAG, "application create...");
		mGlobalApp = this;

		super.onCreate();
	}

	public static GlobalApp getInstance() {
		return mGlobalApp;
	}

}
