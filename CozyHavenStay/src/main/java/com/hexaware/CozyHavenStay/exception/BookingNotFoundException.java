package com.hexaware.CozyHavenStay.exception;

public class BookingNotFoundException extends Exception{
	String msg;
	public BookingNotFoundException(String msg) {
		this.msg=msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
