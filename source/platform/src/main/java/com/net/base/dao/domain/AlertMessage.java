package com.net.base.dao.domain;

public class AlertMessage {
	private AlertMsgType type;
	private String content;

	public AlertMessage() {

	}

	public AlertMessage(AlertMsgType type, String content) {
		this.type = type;
		this.content = content;
	}

	public AlertMsgType getType() {
		return type;
	}

	public void setType(AlertMsgType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}