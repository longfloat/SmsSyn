package com.tong.smssyn;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.evernote.client.android.EvernoteSession;
import com.tong.smssyn.sms.SmsGroup;
import com.tong.smssyn.sms.SmsParser;
import com.tong.smssyn.utils.NoteUtils;
import com.tong.smssyn.utils.SmsGroupsAdapter;

public class SmsGroupsActivity extends Activity {

	private static final String TAG = SmsGroupsActivity.class.getSimpleName();

	private ListView mListView;
	private ArrayList<SmsGroup> mSmsGroups = null;
	private SmsGroupsAdapter mAdapter = null;

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
					NoteUtils.createNote("sms", mSmsGroups.toString(), null);
				} else if (resultCode == Activity.RESULT_CANCELED) {
					Log.d(TAG, "auth failed");
				}
				break;
		}
	}

}
