package com.travel.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;


public class MD5Utils {
	
	/**
	 * 加密
	 * @param password 密码
	 * @return 加密后的密码
	 */
	public static String encodePasword(String password){
			
		//算法 Md5
		String algorithm = "MD5";
		char[]encodeHex = null;
			
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			//返回密文
			byte[] digest = instance.digest(password.getBytes());
			//十六进制加密
			encodeHex = Hex.encodeHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(encodeHex);
	}	

}
