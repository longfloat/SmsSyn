package com.tong.smssyn.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SmsGroup {
	public static final String TAG = SmsGroup.class.getSimpleName();

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
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm",
				Locale.getDefault());

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
