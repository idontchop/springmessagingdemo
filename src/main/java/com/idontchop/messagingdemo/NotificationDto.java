package com.idontchop.messagingdemo;

public class NotificationDto {
	
	public NotificationDto() {}
	public NotificationDto(String from) {
		this.from = from;
		this.to = "TestUser2";
		this.type_id = 1;
	}
	
	String from;
	String to;
	int type_id;
	public String getFrom() {
		return from;
	}
	public NotificationDto setFrom(String from) {
		this.from = from;
		return this;
	}
	public String getTo() {
		return to;
	}
	public NotificationDto setTo(String to) {
		this.to = to;
		return this;
	}
	public int getType_id() {
		return type_id;
	}
	public NotificationDto setType_id(int type_id) {
		this.type_id = type_id;
		return this;
	}
	

}
