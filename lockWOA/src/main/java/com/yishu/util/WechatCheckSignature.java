package com.yishu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author <a href="http://www.yishutech.com">yishu</a>
 * @author <a href="mailto:geysererupt@163.com">admin</a>
 *
 * @since 2017-08-15
 */
public class WechatCheckSignature {
    private static final String token = "yishutech";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arr);
        //生成字符串
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<arr.length;i++){
            stringBuffer.append(arr[i]);
        }
        //sha1加密,获取SHA1值
        String temp=digest(stringBuffer.toString());
        return temp.equals(signature);
    }

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
}