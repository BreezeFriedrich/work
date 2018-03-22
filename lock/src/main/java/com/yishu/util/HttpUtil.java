package com.yishu.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
@Component
public class HttpUtil {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("HttpUtil");

    /*
    public final static String hostName="lock.qixutech.com";
    public static String getIpFromHost(String hostName){
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    private static URL assignUrl;
    private static String assignUrlStr;
    @Value("${assignUrl:https://lock.qixutech.com:2017}")
    public void setAssignUrlStr(String assignUrlStr) {
        HttpUtil.assignUrlStr = assignUrlStr;
    }
    private static URL ownerUrl;
    private static String ownerUrlStr;
    @Value("${ownerUrl:https://43.254.149.28:2017}")
    public void setOwnerUrlStr(String ownerUrlStr) {
        HttpUtil.ownerUrlStr = ownerUrlStr;
    }
    private static URL gatewayUrl;
    private static String gatewayUrlStr;
    @Value("${gatewayUrl:https://43.254.149.28:2017}")
    public void setGatewayUrlStr(String gatewayUrlStr) {
        HttpUtil.gatewayUrlStr = gatewayUrlStr;
    }

    public static String httpsPostToOwner(String data) {
        try {
            ownerUrl=new URL(ownerUrlStr);
            return HttpUtil.httpsPostToURL(ownerUrl,data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long lastFetchMillis=new Date().getTime();
    private static int expireMillis=1000*60/6*1;
    public static String getGatewayIp(String ownerPhoneNumber, String gatewayCode) {
        int reqSign=5;
        LOG.info("sign:"+reqSign+" operation:getGatewayIp");
        String reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        LOG.info("reqData : "+reqData);
        try {
            assignUrl=new URL(assignUrlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String rawData= HttpUtil.httpsPostToURL(assignUrl,reqData);
        LOG.info("rawData : "+rawData);
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(rawData);
            int respSign=rootNode.path("result").asInt();
            LOG.info("respSign:"+String.valueOf(respSign));
            if(0!=respSign){
                return null;
            }
            String gatewayIp = rootNode.path("gatewayIp").asText();
            return gatewayIp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String httpsPostToGateway(String data,String ownerPhoneNumber, String gatewayCode) {
        try {
            gatewayUrl=new URL(gatewayUrlStr);
            if(null==HttpUtil.gatewayUrl || lastFetchMillis+expireMillis<new Date().getTime()){
                String gatewayip=getGatewayIp(ownerPhoneNumber,gatewayCode);
                LOG.info("gatewayip:"+gatewayip);
                if(null==gatewayip){
                    return HttpUtil.httpsPostToURL(gatewayUrl,data);
                }
                try {
                    gatewayUrl=new URL("https://"+gatewayip+":2017/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return HttpUtil.httpsPostToURL(gatewayUrl,data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String httpsPostToGateway(String data) {
        try {
            gatewayUrl=new URL(gatewayUrlStr);
            return HttpUtil.httpsPostToURL(gatewayUrl,data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpsPostToURL(URL url,String data){
        if(LOG.isInfoEnabled()){
            LOG.info("{-->>--url:"+url+"-->>--,data:"+data+"}");
        }
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
            X509TrustManager xtm=new MyTrustManager();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { xtm }, null);
            SSLSocketFactory socketFactory =ctx.getSocketFactory();
            //https-http)
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
        return null;
    }
    /**
     * 向指定ip地址发起https连接. By ChengXinLang
     */
    public static String httpsPostToIp(String ip,String data){
        if(LOG.isInfoEnabled()){
            LOG.info("{-->>--ip:"+ip+"-->>--,data:"+data+"}");
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