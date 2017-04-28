package com.yishu.util;

import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2016/7/11.
 */
public class HttpUtil {

    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(HttpUtil.class);
    //public final static String IP="http://192.168.1.80:2016/";     //192.168.1.54//43.254.149.28

    public final static String hostName="lock.qixutech.com";

    public static String getIp(){
        //return "192.168.1.54";
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postData(String data){
    	String ip=HttpUtil.getIp();
    	String infos=HttpUtil.postData(data, ip);
        logger.info(ip);
        logger.info(infos);
    	return infos;
    }
    
    public static String postData(String data,String ip){
    	System.out.println(ip);
        URL url;
        HttpsURLConnection httpsURLConnection=null;
        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader=null;
        String result="";
        String line;
        //https-http(
        try {
            X509TrustManager xtm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { xtm }, null);
            SSLSocketFactory socketFactory =ctx.getSocketFactory();
          //https-http)

            url=new URL("https://"+ip+":2017/");
            System.out.println("Https");
            httpsURLConnection=(HttpsURLConnection)url.openConnection();

            httpsURLConnection.setSSLSocketFactory(socketFactory);//https-http
            httpsURLConnection.setHostnameVerifier(hostnameVerifier);//https-http
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setConnectTimeout(3000);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestProperty("Content-Type","text/json");
            httpsURLConnection.connect();
            outputStream=httpsURLConnection.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);
            dataOutputStream.write(data.getBytes());
            inputStream=httpsURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream,"GBK");
            bufferedReader=new BufferedReader(inputStreamReader);
            while((line=bufferedReader.readLine())!=null){
                result+=line+"\n";
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
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
            if(httpsURLConnection!=null){
                httpsURLConnection.disconnect();
            }
        }
        return null;
    }



    /**
     public static String postData(String data,String ip){
     URL url;
     HttpURLConnection httpURLConnection=null;
     OutputStream outputStream=null;
     DataOutputStream dataOutputStream=null;
     InputStream inputStream=null;
     InputStreamReader inputStreamReader;
     BufferedReader bufferedReader=null;
     String result="";
     String line;
     try {
     url=new URL("http://"+ip+":2016/");
     httpURLConnection=(HttpURLConnection)url.openConnection();
     httpURLConnection.setRequestMethod("POST");
     httpURLConnection.setConnectTimeout(3000);
     httpURLConnection.setDoOutput(true);
     httpURLConnection.setDoInput(true);
     httpURLConnection.setRequestProperty("Content-Type","text/json");
     httpURLConnection.connect();
     outputStream=httpURLConnection.getOutputStream();
     dataOutputStream=new DataOutputStream(outputStream);
     dataOutputStream.write(data.getBytes());
     inputStream=httpURLConnection.getInputStream();
     inputStreamReader=new InputStreamReader(inputStream,"GBK");
     bufferedReader=new BufferedReader(inputStreamReader);
     while((line=bufferedReader.readLine())!=null){
     result+=line+"\n";
     }
     return result;
     } catch (MalformedURLException e) {
     e.printStackTrace();
     }
     catch (IOException e) {
     e.printStackTrace();
     }
     finally {
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
     **/

}
