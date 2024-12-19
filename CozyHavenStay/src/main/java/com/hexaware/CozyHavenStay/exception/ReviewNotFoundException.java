package com.hexaware.CozyHavenStay.exception;

public class ReviewNotFoundException extends Exception{
	String msg;
	public ReviewNotFoundException(String msg) {
		this.msg=msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
