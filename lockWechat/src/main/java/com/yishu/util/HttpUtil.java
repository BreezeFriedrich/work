package com.yishu.util;

//import org.apache.commons.httpclient.HttpStatus;

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

public class HttpUtil
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("HttpUtil");

    public final static String hostName="lock.qixutech.com";

    public static String httpToGateway(String urlStr){
        HttpURLConnection connection;
        BufferedReader in=null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String result;
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept","Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            connection.setRequestProperty("WechatUser-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            connection.setRequestProperty("WechatUser-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            logger.info("ContentType : "+connection.getContentType());
            logger.info("permission : "+connection.getPermission());
            logger.info("ResponseMessage : "+connection.getResponseMessage());
            int responseCode = connection.getResponseCode();
            logger.info("HTTP连接 responseCode : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                logger.info("responseCode=200,HTTP连接正常");
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null)
                {
                    stringBuffer.append(inputLine);
                }
            } else {
//                logger.info("Can not access the website");
//                logger.info("responseCode="+responseCode+",HTTP连接异常");
                Map map=connection.getHeaderFields();
                for (Object key : map.keySet()) {
                    logger.info("key= "+ key + " and value= " + map.get(key));
                }

                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null)
                {
                    stringBuffer.append(inputLine);
                }
            }
        } catch (MalformedURLException e) {
            logger.info("Wrong URL");
        } catch (IOException e) {
            logger.info("Can not connect");
        }finally {
            if (null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.error("HttpUtil.httpToGateway("+urlStr+")返回值: "+stringBuffer.toString());
            return stringBuffer.toString();
        }
    }

    public static String getQixuIp(){
        return "43.254.149.28";
//        return "112.25.233.122";
        /*
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
        */
    }

    public static String httpsPostToQixu(String data){
//        long time1=new Date().getTime();
        String qixuIp=HttpUtil.getQixuIp();
        URL url = null;
        try {
            url=new URL("https://"+qixuIp+":2017/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        return HttpUtil.doPost(url.toString(),data);
        long time2=new Date().getTime();
//        logger.warn("getQixuIp     用时: "+(time2-time1));
        String result=HttpUtil.httpsPostToIp(qixuIp,data);
        long time3=new Date().getTime();
        logger.warn("httpsPostToIp 用时: "+(time3-time2));

//        logger.info("HTTPS RESPONSE : "+result);
        return result;
    }

    /**
     * 发起get请求并获取结果
     * @param url
     * @return
     */
    public static String doGet(String url)
    {
        String result = null;
        try{
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                result = EntityUtils.toString(response.getEntity());
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向指定ip地址发起https连接. By ChengXinLang
     */
    public static String httpsPostToIp(String ip,String data){
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
            if ("".equals(result)) {
                try {
                    throw new Exception("https连接返回空串");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
        return null;
    }

}

/**
 * 证书信任管理器,用于https请求
 */
class MyTrustManager implements X509TrustManager
{
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {

    }

    public X509Certificate[] getAcceptedIssuers()
    {
        return null;
    }
}