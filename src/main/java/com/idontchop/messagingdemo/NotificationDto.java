package com.idontchop.messagingdemo;

public class NotificationDto {
	
	public NotificationDto() {}
	public NotificationDto(String from) {
		this.fromId = from;
		this.toId = "TestUser2";
		this.type_id = 1;
	}
	
	String fromId;
	String toId;
	int type_id;
	private String referenceId;
	
	
	public String getFromId() {
		return fromId;
	}
	public NotificationDto setFrom(String from) {
		this.fromId = from;
		return this;
	}
	public String getToId() {
		return toId;
	}
	public NotificationDto setTo(String toId) {
		this.toId = toId;
		return this;
	}
	public int getType_id() {
		return type_id;
	}
	public NotificationDto setType_id(int type_id) {
		this.type_id = type_id;
		return this;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	

}
