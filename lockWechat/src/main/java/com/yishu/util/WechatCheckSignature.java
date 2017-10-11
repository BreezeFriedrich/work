/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 处理来自微信的接口认证请求,检验signature,取出echostr返回给微信.
 *
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-28 11:32 admin
 * @since JDK1.7
 */
public class WechatCheckSignature {
    private static final String token = "yishutech";//与微信开发者基本配置服务器配置token一致,参考https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1472017492_58YV5

    /**
     * 加密/校验流程如下：
     1）将token、timestamp、nonce三个参数进行字典序排序
     2）将三个参数字符串拼接成一个字符串进行sha1加密
     3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        /*
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
        */
        /*
        String[] str={token,timestamp,nonce};
        Arrays.sort(str);
        String bigStr=str[0]+str[1]+str[2];
        String digest=digest(bigStr);
        if (digest.equals(signature)){
            return true;
        }
        return false;
        */

        TreeSet<String> set = new TreeSet<String>();
        set.add(token);
        set.add(timestamp);
        set.add(nonce);

        StringBuilder sb = new StringBuilder();
        for (String str : set) {
            sb.append(str);
        }
        String sha = digest(sb.toString());
        System.out.println("sha :"+sha+" || signature :"+signature);

        if (sha.equals(signature) || signature.indexOf(sha) == 2) {
            return true;
        }
        return false;
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
