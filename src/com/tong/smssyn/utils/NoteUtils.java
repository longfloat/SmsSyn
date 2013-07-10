package com.tong.smssyn.utils;

import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.thrift.transport.TTransportException;
import com.tong.smssyn.GlobalApp;

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
			e.printStackTrace();
		}
	}

}
