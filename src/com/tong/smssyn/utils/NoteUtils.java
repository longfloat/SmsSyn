package com.tong.smssyn.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.util.Log;

import com.evernote.client.android.AsyncNoteStoreClient;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;
import com.tong.smssyn.GlobalApp;
import com.tong.smssyn.R;
import com.tong.smssyn.sms.SmsEntity;
import com.tong.smssyn.sms.SmsGroup;

public class NoteUtils {
	private static final String TAG = NoteUtils.class.getSimpleName();

	public static void createSmsNote(final ArrayList<SmsGroup> groups,
			final OnClientCallback<Note> callback) {
		try {

			final AsyncNoteStoreClient ansc = GlobalApp.getEvernoteSession()
					.getClientFactory().createNoteStoreClient();

			ansc.listNotebooks(new OnClientCallback<List<Notebook>>() {

				@Override
				public void onSuccess(List<Notebook> data) {
					// TODO Auto-generated method stub
					boolean hasNotebook = false;
					for (Notebook notebook : data) {
						if (notebook.getName().equals(Constants.APP_NAME)) {
							hasNotebook = true;
							GlobalApp.getInstance().saveNotebookGuid(
									notebook.getGuid());
							break;
						}
					}

					if (hasNotebook) {
						try {
							Log.i(TAG, "notebook existed!");
							saveSmsNote(groups, callback);
						} catch (TTransportException e) {
							// TODO Auto-generated catch block
							Log.d(TAG,
									"has notebook >> create note exception!");
							e.printStackTrace();
						}
					} else {
						Log.i(TAG, "notebook not existed, create it!");
						Notebook notebook = new Notebook();
						notebook.setName(Constants.APP_NAME);
						ansc.createNotebook(notebook,
								new OnClientCallback<Notebook>() {

									@Override
									public void onSuccess(Notebook data) {
										// TODO Auto-generated method stub
										Log.i(TAG, "create notebook success!");
										GlobalApp.getInstance()
												.saveNotebookGuid(
														data.getGuid());
										try {
											saveSmsNote(groups, callback);
										} catch (TTransportException e) {
											// TODO Auto-generated catch block
											Log.d(TAG,
													"has no notebook >> create note exception!");
											e.printStackTrace();
										}
									}

									@Override
									public void onException(Exception e) {
										// TODO Auto-generated method stub
										e.printStackTrace();
										Log.d(TAG,
												"has no notebook >> create notebook exception!");
									}
								});
					}
				}

				@Override
				public void onException(Exception exception) {
					// TODO Auto-generated method stub
					Log.d(TAG, "list noteboos exception!");
					exception.printStackTrace();
				}

			});

		} catch (TTransportException e) {
			// TODO: handle exception
			Log.d(TAG, "asnc inital exception!");
			e.printStackTrace();
		}
	}

	private static void saveSmsNote(ArrayList<SmsGroup> groups,
			OnClientCallback<Note> callback) throws TTransportException {
		Note note = new Note();
		note.setTitle(Constants.NOTE_TITLE_PREFIX + getCurrentDate());
		note.setNotebookGuid(GlobalApp.getInstance().getNotebookGuid());

		StringBuilder builder = new StringBuilder();
		builder.append(EvernoteUtil.NOTE_PREFIX);
		builder.append(formatSmsForNote(groups));
		builder.append(EvernoteUtil.NOTE_SUFFIX);

		note.setContent(builder.toString());

		GlobalApp.getEvernoteSession().getClientFactory()
				.createNoteStoreClient().createNote(note, callback);
	}

	public static String formatSmsForNote(ArrayList<SmsGroup> groups) {
		StringBuilder builder = new StringBuilder();
		for (SmsGroup smsGroup : groups) {
			String address = smsGroup.getAddress();
			builder.append("<h2>");
			builder.append(GlobalApp.getInstance().getString(
					R.string.note_sms_address, address));
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

	private static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss",
				Locale.getDefault());
		String date = sdf.format(c.getTime());
		return date;
	}
}
