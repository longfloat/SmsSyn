package com.tong.smssyn;

import android.app.Application;
import android.util.Log;

import com.evernote.client.android.EvernoteSession;

public class GlobalApp extends Application {

	private static final String TAG = GlobalApp.class.getSimpleName();
	private static final String CONSUMER_KEY = "longfloat";
	private static final String CONSUMER_SECRET = "ad75cc2a81091e78";
	private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

	private static GlobalApp mGlobalApp = null;
	private static EvernoteSession mEvernoteSession = null;

	@Override
	public void onCreate() {
		Log.i(TAG, "application create...");
		mGlobalApp = this;
		mEvernoteSession = EvernoteSession.getInstance(this, CONSUMER_KEY,
				CONSUMER_SECRET, EVERNOTE_SERVICE);

		super.onCreate();
	}

	public static GlobalApp getInstance() {
		return mGlobalApp;
	}

	public static EvernoteSession getEvernoteSession() {
		if (null == mEvernoteSession) {
			Log.d(TAG, "EvernoteSession is null...");
			mEvernoteSession = EvernoteSession.getInstance(mGlobalApp,
					CONSUMER_KEY, CONSUMER_SECRET, EVERNOTE_SERVICE);
		}
		return mEvernoteSession;
	}

}
