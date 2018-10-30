package com.douniu.imshh.common;

public class SessionTimeoutException extends RuntimeException{

	private static final long serialVersionUID = 2241633810087477542L;

	public String toString(){
		return "Session Timeout";
	}
}
