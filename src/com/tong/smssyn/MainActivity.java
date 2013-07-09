package com.tong.smssyn;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.tong.smssyn.sms.SmsGroup;
import com.tong.smssyn.sms.SmsParser;

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();

	private TextView mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mText = (TextView) findViewById(R.id.main_text);
		ArrayList<SmsGroup> groups = SmsParser.getSmsAll();
		mText.setText(groups.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
