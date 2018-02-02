package com.yishu.util;

import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class HttpUtil
{

    public static String postData(String data){
        String ip=HttpUtil.getIpFromDomain(hostName);
        LOG.info("postdata:"+data);
        String infos=HttpUtil.postData(data, ip);
        LOG.info(ip);
        LOG.info(infos);
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

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil() {
        if(LOG.isTraceEnabled()){
            LOG.trace("** HttpUtil()");
        }
    }

    public final static String hostName="lock.qixutech.com";
    public final static String ownerIp="lock.qixutech.com";
    public final static String ADDR_QIXU="https://43.254.149.28:2017";

    public static String getIpFromDomain(String hostName){
//        return "43.254.149.28";
//        return "112.25.233.122";
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpsPostToQixu(String data){
//        long time1=new Date().getTime();
        String qixuIp= HttpUtil.getIpFromDomain(hostName);
        URL url = null;
        try {
            url=new URL("https://"+qixuIp+":2017/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        return HttpUtil.doPost(url.toString(),data);
        long time2=new Date().getTime();
//        LOG.warn("getIpFromDomain     用时: "+(time2-time1));
        String result= HttpUtil.httpsPostToIp(qixuIp,data);
        long time3=new Date().getTime();
        LOG.warn("httpsPostToIp 用时: "+(time3-time2));

//        LOG.info("HTTPS RESPONSE : "+result);
        return result;
    }

    /**
     * 向指定ip地址发起https连接. By ChengXinLang
     */
    public static String httpsPostToIp(String ip,String data){
        if(LOG.isTraceEnabled()){
            LOG.trace(">> httpsPostToIp()");
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("-- httpsPostToIp() > Got ip: {}", ip);
        }
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
            if(LOG.isDebugEnabled()){
                LOG.debug("-- httpsPostToIp() > HTTPS", ip);
            }
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
            if(LOG.isTraceEnabled()){
                LOG.trace("<< httpsPostToIp()");
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                try {
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
            if ("".equals(result)) {
                try {
                    throw new Exception("https连接返回空串");
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error(e.getMessage());
                }
            }
        }
        if(LOG.isTraceEnabled()){
            LOG.trace("<< httpsPostToIp()");
        }
        return null;
    }

}

/**
 * 证书信任管理器,用于https请求
 */
class MyTrustManager implements X509TrustManager{
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException{}
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException{}
    public X509Certificate[] getAcceptedIssuers(){
        return null;
    }
}