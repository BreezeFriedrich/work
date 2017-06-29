package com.yishu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 2017/5/17.
 */
public class HttpUtil {
    private static final Logger logger= LoggerFactory.getLogger(HttpUtil.class);

    public static String postData(String data){
        String ip="127.0.0.1";
//        logger.info(ip);
        URL url;
        HttpURLConnection httpURLConnection=null;
        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader=null;
        String result="";
        String line=null;
        StringBuffer strBuffer=new StringBuffer();
        try {
            url=new URL("http://"+ip+":2017/?characterEncoding=utf8&useSSL=false");
//            logger.info("#CON      ~ "+String.valueOf(url));
//            logger.info("Https");
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestProperty("Content-Type","text/json");
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
//            logger.info("#TAG      ~ "+"connect:..");
            httpURLConnection.connect();
//            logger.info("#TAG      ~ "+"connect:sucesses");
            outputStream=httpURLConnection.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);
//            logger.info("postdata:"+data);
            dataOutputStream.write(data.getBytes());
            inputStream=httpURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
            bufferedReader=new BufferedReader(inputStreamReader);
            while((line=bufferedReader.readLine())!=null){
                strBuffer.append(line+"\n");
            }
            result=new String(strBuffer);
//            logger.info("getData:"+result);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try{
                    inputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(dataOutputStream!=null){
                try{
                    dataOutputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
}
