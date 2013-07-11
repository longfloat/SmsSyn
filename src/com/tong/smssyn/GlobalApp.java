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
	private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

	private static GlobalApp mGlobalApp = null;
	private static EvernoteSession mEvernoteSession = null;
	private static final String NOTEBOOK_GUID = "notebook_guid";

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
					Constants.PREFS_NOTEBOOK_GUID, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(NOTEBOOK_GUID, guid);
			editor.commit();
		}

	}

	public String getNotebookGuid() {
		SharedPreferences pref = getSharedPreferences(
				Constants.PREFS_NOTEBOOK_GUID, Context.MODE_PRIVATE);
		return pref.getString(NOTEBOOK_GUID, null);
	}

}
