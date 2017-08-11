package com.yishu.util;

import java.util.Random;
import java.util.UUID;

//字符串工具
public class StringUtil {

	/***
	 * 判读字符串是否为空值
	 * @param str
	 * @return 为true 表示不是空值，否则是空值
	 */
	public static boolean bIsNotNull(String str) {
		if(str==null || str.trim().equalsIgnoreCase("null") || str.trim().equals(""))
			return false;
		else 
			return true;
	}

	/**
	 * 如果字符串为null，则替换为空字符
	 * @param str
	 * @return
	 */
	public static String replaceNull(String str) {
		if(str==null || str.trim().equalsIgnoreCase("null") || str.trim().equals(""))
			return "";
		else 
			return str;
	}

	/**
	 * JAVA生成的唯一号，用于记录的监测终端唯一编码,去掉“-”
	 * 
	 * @return 32位字符
	 */
	public static String getT_UUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	public static String getRandomString(int length){ //length表示生成字符串的长度  
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
