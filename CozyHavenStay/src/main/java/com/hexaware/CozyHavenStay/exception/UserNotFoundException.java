package com.hexaware.CozyHavenStay.exception;

public class UserNotFoundException extends Exception{
	String msg;
	public UserNotFoundException(String msg) {
		this.msg=msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
