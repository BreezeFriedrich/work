package com.yishu.util;

import com.yishu.pojo.AccessToken;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WechatUtil {
    private static final String APPID="wx6eo2v3fd4af3";
    private static final String APPSECRET="c7058bjh8g97m87cf8a2a7632f800a1";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken accessToken=new AccessToken();
        String url=ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject=HttpComponent.doGetStr(url);
        if(null!=jsonObject){
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }

    public static String upload(String filePath,String accessToken,String type) throws IOException {
        File file = new File(filePath);
        if(!file.exists()||!file.isFile()){
            throw new IOException("文件不存在");
        }
        String urlStr=UPLOAD_URL.replace("ACCESS_TOKEN",accessToken).replace("TYPE",type);
        URL url=new URL(urlStr);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        //设置请求头信息
        conn.setRequestProperty("Connection","Keep-Alive");
        conn.setRequestProperty("Charset","UTF-8");
        //设置边界
        String BOUNDARY = "----------"+System.currentTimeMillis();
        conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);

        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("--");
        stringBuffer.append(BOUNDARY);
        stringBuffer.append("\r\n");
        stringBuffer.append("Content-Disposition:form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
        stringBuffer.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = stringBuffer.toString().getBytes("UTF-8");

        OutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(head); //输出表头

        DataInputStream in =new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] byteArr = new byte[1024];
        while ( -1 != (bytes=in.read(byteArr)) ){ //从输入流in中读取下一个字节,并存储到字节数组byteArr,返回值为下一个字节或者(流末尾)-1.
            out.write(byteArr,0,bytes);
        }
        in.close();

        //结尾部分
        byte[] foot=("\r\n--"+BOUNDARY+"--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        StringBuffer strBuff = new StringBuffer();
        BufferedReader bufferedReader=null;
        String result=null;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line=null;
            while ( null !=(line=bufferedReader.readLine()) ){
                strBuff.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bufferedReader){
                bufferedReader.close();
            }
        }
        if(null == result){
            result = strBuff.toString();
        }

        JSONObject jsonObject = JSONObject.fromObject(result);
        String media_id = jsonObject.getString("media_id");
        return media_id;
    }
}