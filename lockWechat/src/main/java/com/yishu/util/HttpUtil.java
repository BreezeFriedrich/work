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
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtil
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("HttpUtil");

    public final static String hostName="lock.qixutech.com";

    public static String getQixuIp(){
        //return "192.168.1.54";
        try {
            InetAddress inetAddress=InetAddress.getByName(hostName);
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpsPostToQixu(String data){
        String qixuIp=HttpUtil.getQixuIp();
        URL url = null;
        try {
            url=new URL("https://"+qixuIp+":2017/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        return HttpUtil.doPost(url.toString(),data);
        return HttpUtil.httpsPostToIp(qixuIp,data);
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
     * 发起post请求并获取结果
     * 
     * @param url
     *            请求地址
     * @param json
     *            请求json数据
     * 
     */
    public static String doPost(String url, String json)
    {
        String responseContent = null; // 响应内容
        try{
            HttpPost request = new HttpPost(url); // 根据地址获取请求
            StringEntity myEntity = new StringEntity(json, ContentType.APPLICATION_JSON);// 构造请求数据
            request.setEntity(myEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
                return responseContent;
            }
        }
        catch (IOException e)
        {
            System.err.println("! Exception : post request error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * 
     */
    public static String doHttps(String requestUrl, String requestMethod, String outputStr)
    {
        StringBuffer buffer = new StringBuffer();
        try{
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm ={ new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr)
            {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            return buffer.toString();
        }
        catch (Exception e)
        {
            System.out.println("HttpsConnectException");
            e.printStackTrace();
        }
        return null;
    }

    // public static void main(String[] args) {
    // HttpUtil util = new HttpUtil();
    // String str = util
    // .getUrl("https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code");
    // System.out.println(str);
    // }

    //ChengXinLang
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

/*
//
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