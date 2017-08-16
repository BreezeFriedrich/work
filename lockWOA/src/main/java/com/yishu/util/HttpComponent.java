package com.yishu.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpComponent {

    /**
     * get请求
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        JSONObject jsonObject=null;
        try {
            HttpResponse httpResponse=httpClient.execute(httpGet);
            HttpEntity entity=httpResponse.getEntity();
            if(null!=entity){
                String result= EntityUtils.toString(entity,"UTF-8");
                jsonObject =JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        JSONObject jsonObject=null;
        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse httpResponse=httpClient.execute(httpPost);
            HttpEntity entity=httpResponse.getEntity();
            if(null!=entity){
                String result=EntityUtils.toString(entity,"UTF-8");
                jsonObject=JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}