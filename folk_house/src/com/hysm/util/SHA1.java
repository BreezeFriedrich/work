package com.hysm.util;

import java.security.*;
import java.util.TreeSet;

public class SHA1 {

	public static String digest(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}

	public static boolean authWXDEV(String signature, String timestamp, String nonce, String token) {

		TreeSet<String> set = new TreeSet<String>();
		set.add(token);
		set.add(timestamp);
		set.add(nonce);

		StringBuilder sb = new StringBuilder();
		for (String str : set) {
			sb.append(str);
		}
		String sha = SHA1.digest(sb.toString());
		System.out.println("sha :"+sha+" || signature :"+signature);

		if (sha.equals(signature) || signature.indexOf(sha) == 2) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		/*
		TreeSet<String> set = new TreeSet<String>();
		set.add("140219489812222222222222adfassafdsafdsQQ666666sfasafsfsafsa");
		set.add("584327103");
		set.add("aaren0125ITREN");
		StringBuilder sb = new StringBuilder();
		for (String str : set) {
			sb.append(str);
		}
		String str = SHA1.digest(sb.toString());
		System.out.println(str + "[" + str.length() + "]");
		*/
		boolean isvalid=authWXDEV("5d5523cff5c877dee6cdbcff699dc3b8e28fa3","1502072231000","4983","aaren0125ITREN");
		System.out.println("WechatDevUrlAuth : "+isvalid);
	}

	//url : http://112.25.233.122:8000/folk_house/wxurl/wxurl!verify.do?signature=3F5D5523CFF5C877DEE6CDBCFF699DC3B8E28FA3&timestamp=1502072231000&nonce=4983&echostr=aejhreajh
}
