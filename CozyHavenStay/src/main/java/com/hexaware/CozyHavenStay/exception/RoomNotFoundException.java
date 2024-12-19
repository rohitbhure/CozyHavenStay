package com.hexaware.CozyHavenStay.exception;

public class RoomNotFoundException extends Exception{
	String msg;
	public RoomNotFoundException(String msg) {
		this.msg=msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
