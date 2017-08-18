package com.hysm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;

public class Upload {
	

	    public  String upload_url(File[] file1,String filename,String [] fileFileName) {
	        FileOutputStream fos = null;
	        FileInputStream fis = null; 
	        String url="";
	        File file=null;
	        for(File f:file1){
	        	if(f!=null){
	        		file=f;
	        	}
	        }
	        String fileName = "";// 文件全名
	        for(String s:fileFileName){
	        	if(s!=null && !s.equals("")){
	        		fileName=s;
	        	}
	        }
	        
			String file_name="";
			String file_suufic="";
			int dot = fileName.lastIndexOf('.'); 
	        if ((dot >-1) && (dot < (fileName.length()))) { 
	        	 file_name=fileName.substring(0, dot); 
	        } 
	        file_suufic=fileName.substring(dot+1,fileName.length()); 
			String ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
			long time=System.currentTimeMillis();
	        try {
	            // 建立文件输出流
	            fos = new FileOutputStream(ServletActionContext.getServletContext().getRealPath("") + "\\"+filename+"\\" +time+ext);
	            url=ServletActionContext.getServletContext().getRealPath("") + "\\"+filename+"\\" + time+ext;
	            // 建立文件上传流
	            fis = new FileInputStream(file);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = fis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	        } catch (Exception e) {
	            System.out.println("文件上传失败");
	            e.printStackTrace();
	        } finally {
	            try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	        }
	        return url;
	    }

	   
}
