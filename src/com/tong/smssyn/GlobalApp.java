package com.tong.smssyn;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.evernote.client.android.EvernoteSession;
import com.tong.smssyn.utils.Constants;

public class GlobalApp extends Application {

	private static final String TAG = GlobalApp.class.getSimpleName();
	private static final String CONSUMER_KEY = "longfloat";
	private static final String CONSUMER_SECRET = "ad75cc2a81091e78";
	// private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE =
	// EvernoteSession.EvernoteService.SANDBOX;
	private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.PRODUCTION;

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

	public void saveNotebookGuid(String guid) {
		if (!TextUtils.isEmpty(guid)) {
			SharedPreferences pref = getSharedPreferences(
Constants.PREFS_NAME,
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(Constants.PREFS_NOTEBOOK_GUID, guid);
			editor.commit();
		}

	}

	public String getNotebookGuid() {
		SharedPreferences pref = getSharedPreferences(
Constants.PREFS_NAME,
				Context.MODE_PRIVATE);
		return pref.getString(Constants.PREFS_NOTEBOOK_GUID, null);
	}

	public boolean isAuthed() {
		SharedPreferences pref = getSharedPreferences(Constants.PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getBoolean(Constants.PREFS_IS_AUTHED, false);
	}

	public void setHasAuthed(boolean isAuthed) {
		SharedPreferences pref = getSharedPreferences(Constants.PREFS_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(Constants.PREFS_IS_AUTHED, isAuthed);
		editor.commit();
	}

}
