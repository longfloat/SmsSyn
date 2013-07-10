package com.tong.smssyn;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.tong.smssyn.sms.SmsGroup;
import com.tong.smssyn.sms.SmsParser;
import com.tong.smssyn.utils.NoteUtils;
import com.tong.smssyn.utils.SmsGroupsAdapter;

public class SmsGroupsActivity extends Activity {

	private static final String TAG = SmsGroupsActivity.class.getSimpleName();

	private ListView mListView;
	private ArrayList<SmsGroup> mSmsGroups = null;
	private SmsGroupsAdapter mAdapter = null;
	private ProgressDialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_groups);

		mListView = (ListView) findViewById(R.id.sms_groups_listview);

		mSmsGroups = SmsParser.getSmsAll();
		mAdapter = new SmsGroupsAdapter(getApplicationContext(), mSmsGroups);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sms_groups, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_sync:
				if (GlobalApp.getEvernoteSession().isLoggedIn()) {
					Log.d(TAG, "evernote client has been authed!");
					mDialog = new ProgressDialog(SmsGroupsActivity.this);
					mDialog.setTitle(R.string.menu_sync);
					mDialog.setMessage(getString(R.string.promp_sync_sms));
					mDialog.show();
					// NoteUtils.createNote("sms_format", mSmsGroups.toString(), mCallback);
					NoteUtils.createNoteFromGroups("sms_format", mSmsGroups, mCallback);
				} else {
					GlobalApp.getEvernoteSession().authenticate(
							GlobalApp.getInstance());
				}
				break;

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case EvernoteSession.REQUEST_CODE_OAUTH:
				// 印象笔记授权返回结果
				if (resultCode == Activity.RESULT_OK) {
					Log.d(TAG, "auth successful");
				} else if (resultCode == Activity.RESULT_CANCELED) {
					Log.d(TAG, "auth failed");
				}
				break;
		}
	}
	
	private OnClientCallback<Note> mCallback = new OnClientCallback<Note>() {

		@Override
		public void onSuccess(Note data) {
			// TODO Auto-generated method stub
			Log.i(TAG, "sync success...");
			if (mDialog.isShowing()) {
				mDialog.dismiss();
			}
			Toast.makeText(GlobalApp.getInstance(), R.string.promp_sync_success, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onException(Exception exception) {
			// TODO Auto-generated method stub
			Log.i(TAG, "sync fail...");
			if (mDialog.isShowing()) {
				mDialog.dismiss();
			}
			Toast.makeText(GlobalApp.getInstance(), R.string.promp_sync_fail, Toast.LENGTH_SHORT).show();
			exception.printStackTrace();
		}
	};

}
