package com.douniu.imshh.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUnit {
	public static String encrypt(String src){
		MessageDigest md = null;
		String target = "";
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(src.getBytes());
			target = byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return target;
	}
	
	private static String byte2hex(byte[] digest){
		String hs = "";
		String tmp = "";
		for (int i = 0; i < digest.length; i++){
			tmp = Integer.toHexString(digest[i] & 0XFF);
			if (tmp.length() == 1){
				hs += "0" + tmp;
			}else{
				hs += tmp;
			}
		}
		return hs;
	}
}
