package com.tong.smssyn.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class SmsGroup {
	public static final String TAG = SmsGroup.class.getSimpleName();
	private static final long DAY_MILLISEC = 86400;

	private String address;
	private int sync;
	private ArrayList<SmsEntity> sms = null;

	public SmsGroup(String address, ArrayList<SmsEntity> sms) {
		this.address = address;
		this.sync = 0;

		if (sms.size() == 0) {
			throw new IllegalArgumentException("empty sms!");
		} else {
			this.sms = sms;
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLastDate() {
		long lastDate = sms.get(sms.size() - 1).getDate();
		//Log.d(TAG, "lastDate=" + String.valueOf(lastDate));
		long current = Calendar.getInstance().getTimeInMillis();
		//Log.d(TAG, "current=" + String.valueOf(current));
		SimpleDateFormat sdf = null;

		if (current - lastDate > DAY_MILLISEC) {
			sdf = new SimpleDateFormat("MM-dd", Locale.getDefault());
		} else {
			sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());
		}

		return sdf.format(new Date(lastDate));
	}

	public String getLastBody() {
		return sms.get(sms.size() - 1).getBody();
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public ArrayList<SmsEntity> getSms() {
		return sms;
	}

	public void setSms(ArrayList<SmsEntity> sms) {
		this.sms = sms;
	}

	public int getCount() {
		if (sms != null) {
			return sms.size();
		}

		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(address).append("-->").append(sms.toString());

		return builder.toString();
	}

}
