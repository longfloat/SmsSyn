package com.tong.smssyn.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import com.tong.smssyn.GlobalApp;

public class SmsParser {
	private static final String TAG = SmsParser.class.getSimpleName();
	
	private static final String SMS_URI_ALL = "content://sms/";
	
	/**
	 * 获取手机中的短消息,结果按照发件人进行分类
	 * 
	 * @return 获取到的短消息
	 */
	public static ArrayList<SmsGroup> getSmsAll() {
		ArrayList<SmsGroup> smsGroups = null;

		try {
			Uri uri = Uri.parse(SMS_URI_ALL);
			String[] projection = new String[] { SmsEntity.ID,
					SmsEntity.ADDRESS, SmsEntity.DATE, SmsEntity.BODY,
					SmsEntity.PROTOCOL, SmsEntity.TYPE };
			final String SORT_ORDER = "date desc";
			Cursor cursor = GlobalApp.getInstance().getContentResolver()
					.query(uri, projection, null, null, SORT_ORDER);

			if (cursor.moveToFirst()) {
				int indexId = cursor.getColumnIndex(SmsEntity.ID);
				int indexAddress = cursor.getColumnIndex(SmsEntity.ADDRESS);
				int indexDate = cursor.getColumnIndex(SmsEntity.DATE);
				int indexBody = cursor.getColumnIndex(SmsEntity.BODY);
				int indexProtocol = cursor.getColumnIndex(SmsEntity.PROTOCOL);
				int indexType = cursor.getColumnIndex(SmsEntity.TYPE);

				smsGroups = new ArrayList<SmsGroup>();
				Map<String, ArrayList<SmsEntity>> map = new HashMap<String, ArrayList<SmsEntity>>();

				do {
					int id = cursor.getInt(indexId);
					String address = cursor.getString(indexAddress).replace(
							"+86", "");
					long date = cursor.getLong(indexDate);
					String body = cursor.getString(indexBody);
					int protocol = cursor.getInt(indexProtocol);
					int type = cursor.getInt(indexType);

					SmsEntity smsEntity = new SmsEntity(id, address, body,
							date, type, protocol);
					Log.d(TAG, smsEntity.toString());
					if (map.containsKey(address)) {
						map.get(address).add(smsEntity);
					} else {
						ArrayList<SmsEntity> smsEntities = new ArrayList<SmsEntity>();
						smsEntities.add(smsEntity);
						map.put(address, smsEntities);
					}
				} while (cursor.moveToNext());

				for (Map.Entry<String, ArrayList<SmsEntity>> entry : map
						.entrySet()) {
					SmsGroup group = new SmsGroup(entry.getKey(),
							entry.getValue());
					smsGroups.add(group);
				}
			}

		} catch (SQLiteException e) {
			// TODO: handle exception
			Log.d(TAG, e.getCause().toString());
		}

		return smsGroups;
		
	}

}
