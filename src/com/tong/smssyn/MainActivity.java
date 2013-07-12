package com.tong.smssyn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private Button mAuthButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (GlobalApp.getInstance().isAuthed()) {
			Intent intent = new Intent(MainActivity.this,
					SmsGroupsActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			setContentView(R.layout.activity_main);

			mAuthButton = (Button) findViewById(R.id.main_button_auth);
			mAuthButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					GlobalApp.getEvernoteSession().authenticate(
							MainActivity.this);
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case EvernoteSession.REQUEST_CODE_OAUTH:
				// 印象笔记授权返回结果
				if (resultCode == Activity.RESULT_OK) {
					Log.d(TAG, "auth successful");
					Toast.makeText(MainActivity.this, R.string.auth_success,
							Toast.LENGTH_SHORT).show();
					GlobalApp.getInstance().setHasAuthed(true);
					Intent intent = new Intent(MainActivity.this,
							SmsGroupsActivity.class);
					startActivity(intent);
					this.finish();
				} else if (resultCode == Activity.RESULT_CANCELED) {
					Log.d(TAG, "auth failed");
					Toast.makeText(MainActivity.this, R.string.auth_fail,
							Toast.LENGTH_SHORT).show();
					GlobalApp.getInstance().setHasAuthed(false);
				}
				break;
		}
	}

}
