package com.tong.smssyn.utils;

import java.util.ArrayList;

import android.R.integer;
import android.util.Log;

import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.thrift.transport.TTransportException;
import com.tong.smssyn.GlobalApp;
import com.tong.smssyn.R;
import com.tong.smssyn.sms.SmsEntity;
import com.tong.smssyn.sms.SmsGroup;

public class NoteUtils {

	private static final String TAG = NoteUtils.class.getSimpleName();

	public static void createNote(String title, String content,
			OnClientCallback<Note> callback) {
		Note note = new Note();
		note.setTitle(title);

		StringBuilder builder = new StringBuilder();
		builder.append(EvernoteUtil.NOTE_PREFIX);
		builder.append(content);
		builder.append(EvernoteUtil.NOTE_SUFFIX);

		note.setContent(builder.toString());

		try {
			GlobalApp.getEvernoteSession().getClientFactory()
					.createNoteStoreClient().createNote(note, callback);

		} catch (TTransportException e) {
			// TODO: handle exception
			Log.d(TAG, e.getCause().toString());
			e.printStackTrace();
		}
	}
	
	public static void createNoteFromGroups(String title, ArrayList<SmsGroup> groups,
			OnClientCallback<Note> callback) {
		Note note = new Note();
		note.setTitle(title);

		StringBuilder builder = new StringBuilder();
		builder.append(EvernoteUtil.NOTE_PREFIX);
		builder.append(formatSmsFromGroups(groups));
		builder.append(EvernoteUtil.NOTE_SUFFIX);

		note.setContent(builder.toString());

		try {
			GlobalApp.getEvernoteSession().getClientFactory()
					.createNoteStoreClient().createNote(note, callback);

		} catch (TTransportException e) {
			// TODO: handle exception
			Log.d(TAG, e.getCause().toString());
			e.printStackTrace();
		}
	}
	
	public static String formatSmsFromGroups(ArrayList<SmsGroup> groups) {
		StringBuilder builder = new StringBuilder();
		for (SmsGroup smsGroup : groups) {
			String address = smsGroup.getAddress();
			builder.append("<h2>");
			builder.append(GlobalApp.getInstance().getString(R.string.note_sms_address, address));
			builder.append("</h2><hr/>");
			ArrayList<SmsEntity> smsEntities = smsGroup.getSms();
			
			for (SmsEntity smsEntity : smsEntities) {
				int type = smsEntity.getType();
				if (type == 2) {
					builder.append("<p><b><span style=\"color:#0099cc\">我: ");
					builder.append(smsEntity.getFormatDate());
					builder.append("</span></b></p>");
					builder.append("<p>");
					builder.append("<span style=\"color:#0099cc\">");
					builder.append(smsEntity.getBody());
					builder.append("</span></p>");
				} else if (type == 1) {
					builder.append("<p><b>");
					builder.append(address);
					builder.append(": ");
					builder.append(smsEntity.getFormatDate());
					builder.append("</b></p>");
					builder.append("<p>");
					builder.append(smsEntity.getBody());
					builder.append("</p>");
				}
			}
			
			builder.append("<br/>");
		}
		return builder.toString();
	}

}
