package com.yishu.util;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

//import org.apache.tomcat.util.codec.binary.Base64;

public class NoteUtil {
	
	/**
	 * ���һ���������
	 * @return
	 */
	public static String createToken(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	/**
	 *ID
	 * @return
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	/**
	 * md5
	 * @param msg ����
	 * @return 
	 */
	public static String md5(String msg){
		try {
			//md5
			MessageDigest md = 
				MessageDigest.getInstance("MD5");
			byte[] output = md.digest(msg.getBytes());
//			System.out.println(output.length);
			//����Base64
			return Base64.encodeBase64String(output);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public static Long DateStringToLong(String dateStr){
		if(dateStr == null || "".equals(dateStr)){
			return null;
		}
		//��begin��long
		DateFormat df = DateFormat.getDateInstance();
		Date beginTime;
		try {
			beginTime = df.parse(dateStr);
			return beginTime.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
//		System.out.println(md5("123456"));
//		System.out.println(md5("abcsssssssssssxxxxx"));
		System.out.println(createToken());
	}
	
}
