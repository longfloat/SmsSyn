package com.tong.smssyn.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmsEntity {

	public static final String ID = "_id";
	public static final String ADDRESS = "address";
	public static final String BODY = "body";
	public static final String DATE = "date";
	public static final String TYPE = "type";
	public static final String PROTOCOL = "protocol";

	public SmsEntity(int id, String address, String body, long date, int type,
			int protocol) {
		this.id = id;
		this.address = address;
		this.body = body;
		this.date = date;
		this.type = type;
		this.protocol = protocol;
	}

	/**
	 * 短消息序号
	 */
	private int id;

	/**
	 * 消息发送方
	 */
	private String address;

	/**
	 * 消息内容
	 */
	private String body;

	/**
	 * 消息类型
	 * <ul>
	 * <li>1: 接收</li>
	 * <li>2: 发出</li>
	 * </ul>
	 */
	private int type;

	/**
	 * 消息发送时间
	 */
	private long date;

	/**
	 * 消息协议
	 * <ul>
	 * <li>0 SMS_PROTO 短信</li>
	 * <li>1 MMS_PROTO 彩信</li>
	 * </ul>
	 */
	private int protocol;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getDate() {
		return date;
	}

	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm",
				Locale.getDefault());
		return sdf.format(new Date(date));
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ID).append(":").append(id).append(",");
		builder.append(ADDRESS).append(":").append(address).append(",");
		builder.append(DATE).append(":").append(date).append(",");
		builder.append(body).append(":").append(body).append(",");
		builder.append(type).append(":").append(type);

		return builder.toString();
	}
}
