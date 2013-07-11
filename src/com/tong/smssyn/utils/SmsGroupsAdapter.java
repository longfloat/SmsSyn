package com.tong.smssyn.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tong.smssyn.R;
import com.tong.smssyn.sms.SmsGroup;

public class SmsGroupsAdapter extends BaseAdapter {

	private ArrayList<SmsGroup> mSmsGroups = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public SmsGroupsAdapter(Context context, ArrayList<SmsGroup> groups) {
		this.mContext = context;
		this.mSmsGroups = groups;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSmsGroups.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSmsGroups.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		SmsGroup group = mSmsGroups.get(position);

		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.item_sms_groups, null);
			viewHolder = new ViewHolder();
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.item_sms_groups_address);
			viewHolder.count = (TextView) convertView
					.findViewById(R.id.item_sms_groups_count);
			viewHolder.body = (TextView) convertView
					.findViewById(R.id.item_sms_groups_body);
			viewHolder.lastDate = (TextView) convertView
					.findViewById(R.id.item_sms_groups_date);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.address.setText(group.getAddress());
		viewHolder.count.setText(String.valueOf(group.getCount()));
		viewHolder.body.setText(group.getLastBody());
		viewHolder.lastDate.setText(group.getLastDate());

		return convertView;
	}

	static class ViewHolder {
		TextView address, body, lastDate, count;
	}

}
