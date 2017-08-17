package com.yishu.util;

import com.yishu.domain.FileUploadResult;
import com.yishu.domain.UploadFailInfo;
import com.yishu.domain.UploadSuccessInfo;
import net.sf.json.JSONObject;

import java.io.File;

/**
 * 文件上传与微信结合,返回上传文件到微信的结果 FileUploadResult.
 *
 * @author admin
 * @since 2017-08-17
 */
public class FileUpload {
    private static final String upload_url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public static FileUploadResult Upload(String access_token,String type,File file){
        FileUploadResult fileUploadResult=null;
        String url=upload_url.replace("ACCESS_TOKEN",access_token).replace("TYPE",type);
        try {
            HttpFileUploadUtil httpFileUploadUtil=new HttpFileUploadUtil(url);
            String result=httpFileUploadUtil.send();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if(jsonObject.containsKey("media_id")){
                fileUploadResult.setSuccessful(true);
                UploadSuccessInfo uploadSuccessInfo=new UploadSuccessInfo();
                uploadSuccessInfo.setMedia_id(jsonObject.getString("media_id"));
                uploadSuccessInfo.setType(jsonObject.getString("type"));
                uploadSuccessInfo.setCreated_at(jsonObject.getString("created_at"));
                fileUploadResult.setInfo(uploadSuccessInfo);
            }else {
                fileUploadResult.setSuccessful(false);
                UploadFailInfo uploadFailInfo=new UploadFailInfo();
                uploadFailInfo.setErrcode(jsonObject.getString("errcode"));
                uploadFailInfo.setErrmsg(jsonObject.getString("errmsg"));
                fileUploadResult.setInfo(uploadFailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileUploadResult.setSuccessful(false);
            UploadFailInfo uploadFailInfo=new UploadFailInfo();
            uploadFailInfo.setErrmsg("Upload Exception: "+e.toString());
            fileUploadResult.setInfo(uploadFailInfo);
        }
        return fileUploadResult;
    }
}