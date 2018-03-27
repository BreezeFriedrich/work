package com.yishu.util;

//import org.apache.commons.httpclient.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Map;
@Component
public class HttpUtil{

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("HttpUtil");

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
    private static int expireMillis=1000*60*30;
    public static String getGatewayIp(String ownerPhoneNumber, String gatewayCode) {
        int reqSign=5;
        String reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        LOG.info("reqData:"+reqData);
        try {
            assignUrl=new URL(assignUrlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String rawData= HttpUtil.httpsPostToURL(assignUrl,reqData);
        LOG.info("rawData:"+rawData);
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(rawData);
            int respSign=rootNode.path("result").asInt();
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
                gatewayUrl=new URL("https://"+gatewayip+":2017/");
            }
            return HttpUtil.httpsPostToURL(gatewayUrl,data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String httpsPostToGateway(String data){
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
//            System.out.println("Https");
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

    public static String doGet(String url){
        String result = null;
        try{
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /*
    public static String getIpFromHost(String hostName){
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    //连接局域网内的网关,当跨域访问时无效所以废弃.
    public static String httpToGateway(String urlStr){
        HttpURLConnection connection;
        BufferedReader in=null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String result;
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("WechatUser-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            LOG.info("ContentType : "+connection.getContentType());
            LOG.info("permission : "+connection.getPermission());
            LOG.info("ResponseMessage : "+connection.getResponseMessage());
            int responseCode = connection.getResponseCode();
            LOG.info("HTTP连接 responseCode : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                LOG.info("responseCode=200,HTTP连接正常");
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null)
                {
                    stringBuffer.append(inputLine);
                }
            } else {
//                LOG.info("Can not access the website");
//                LOG.info("responseCode="+responseCode+",HTTP连接异常");
                Map map=connection.getHeaderFields();
                for (Object key : map.keySet()) {
                    LOG.info("key= "+ key + " and value= " + map.get(key));
                }

                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null)
                {
                    stringBuffer.append(inputLine);
                }
            }
        } catch (MalformedURLException e) {
            LOG.info("Wrong URL");
        } catch (IOException e) {
            LOG.info("Can not connect");
        }finally {
            if (null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LOG.error("HttpUtil.httpToGateway("+urlStr+")返回值: "+stringBuffer.toString());
            return stringBuffer.toString();
        }
    }
    */
}

/**
 * 证书信任管理器,用于https请求
 */
class MyTrustManager implements X509TrustManager{
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException{}
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException{}
    public X509Certificate[] getAcceptedIssuers(){return null;}
}