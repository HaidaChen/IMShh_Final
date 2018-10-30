package com.douniu.imshh.common;

public class NoPermissionException extends RuntimeException{

	private static final long serialVersionUID = -8493185277451557698L;

	public String toString(){
		return "has no permission";
	}
}
