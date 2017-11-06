package com.yishu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by admin on 2017/5/17.
 */
public class HttpUtil {
    private static final Logger logger= LoggerFactory.getLogger(HttpUtil.class);

    private static final String urlStr;
    static{
        /*
//        本地测试 String propertiesPath = "E:\\install\\Apache\\Tomcat 7.0\\appConf\\swipe\\swipe.properties";
//        String propertiesPath = "/home/qixu/prods/swipe/swipe.properties";
        String propertiesPath = "/usr/local/tomcat/appConf/swipe/swipe.properties";
//失败        String propertiesPath = "src/main/db.properties";
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(propertiesPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties pro=new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlStr=pro.getProperty("httpServer.url");
        */

        String propertiesPath="db.properties";
        InputStream in = null;
        in = HttpUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
        Properties pro=new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlStr=pro.getProperty("httpServer.url");
    }

    public static String postData(String data){
//        String ip="127.0.0.1";

//        logger.info(ip);
        URL url=null;

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
//            String ip="43.254.149.29";
//            url=new URL("http://"+ip+":2017/?characterEncoding=utf8&useSSL=false");
            url=new URL(urlStr);
//            logger.info("Http Connection url: "+String.valueOf(url));
//            logger.info("Http");
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestProperty("Content-Type","text/json");
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.connect();
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