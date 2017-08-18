package com.hysm.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Element;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

public class ImagesTool {
	public static Map<String,String> fileUpload(){
		HttpServletRequest request = ServletActionContext.getRequest();
		   String name="";
	       DiskFileItemFactory factory = new DiskFileItemFactory();
	       ServletFileUpload servletFileUpload = new ServletFileUpload(factory);//通过工厂生成一个处理文件上传的servlet对象
	      Map<String, String> map=new HashMap<String, String>();
	       try {
	           List fileItemList = servletFileUpload.parseRequest(request);//解析request
	           Iterator iterator = fileItemList.iterator();
	           while (iterator.hasNext()) {
	        	   System.out.println("进入1123");
	               FileItem item = (FileItem) iterator.next();
	               
	               name=item.getName();
	               map.put("name", name);
	               if(item.isFormField()){//表单的参数字段
	            	   map.put("id", item.getString("UTF-8"));
	                   System.out.println("表单的参数名称："+item.getFieldName()+",表单的参数值："+item.getString("UTF-8"));
	               }else {
	                   if(item.getName()!=null && !item.getName().equals("")){//一个上传的文件
	                	   name = item.getName();//获取名字
	           	        
	      	             System.out.println(name);
	      	        
	      	             String t_ext = name.substring(name.lastIndexOf(".") + 1);
	      					
	      					if(!t_ext.equalsIgnoreCase("jpg")
	      							&&!t_ext.equalsIgnoreCase("jpeg")
	      							&&!t_ext.equalsIgnoreCase("png")
	      							&&!t_ext.equalsIgnoreCase("gif")
	      							&&!t_ext.equalsIgnoreCase("bmp")
	      							&&!t_ext.equalsIgnoreCase("xls")
	      							&&!t_ext.equalsIgnoreCase("xlsx")
	      							)
	      					{
	      	                   
	      						item.delete();
	      	                 
	      	                 }
	      	        
	      	        
	      	         
	      	         
	      	        
	      	          String savepath = request.getSession().getServletContext().getRealPath("/")+"/excel/";
	      			/*
	      			 * 2. 创建目标文件
	      			 */
	      			name=System.currentTimeMillis()+name;
	      			System.out.println(savepath);
	      			 System.out.println(name);
	      			 File destFile = new File(savepath, name);
	      			/*
	      			 * 3. 保存文件
	      			 */
	      			try {
	      			
	      				item.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
	      				/*int row_num=excel.read1sql1(savepath+name);*/
	      			} catch (Exception e) {
	      				
	      				e.printStackTrace();
	      			
	      			}
	                	   
	                      request.setAttribute("message", "上传文件成功！");
	              }else{
	                      request.setAttribute("message", "没有选择上传文件！");
	                 }
	               }
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	           request.setAttribute("message", "上传文件失败！");
	       }
	       return map;
	   }
	
	public static void createThumbnail(String src, String dist, float width,
            float height) {
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
                return;
            }
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例
            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width) {
                if (image.getHeight() > image.getWidth()) {
                    ratio = height / image.getHeight();
                } else {
                    ratio = width / image.getWidth();
                }
            }
            // 计算新的图面宽度和高度
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);

            BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
                    BufferedImage.TYPE_INT_RGB);
            bfImage.getGraphics().drawImage(
                    image.getScaledInstance(newWidth, newHeight,
                            Image.SCALE_SMOOTH), 0, 0, null);

            FileOutputStream os = new FileOutputStream(dist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bfImage);
            os.close();
            System.out.println("创建缩略图成功");
        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }
    }
	
	
	
	 public static void img_change(String url,String name) 
	    { 
	            Tosmallerpic(url,new File(url+name),"_small",name,30,40,(float)0.7); 
	    } 
	     
	    /** 
	    * 
	    * @param f 图片所在的文件夹路径 
	    * @param file 图片路径 
	    * @param ext 扩展名 
	    * @param n 图片名 
	    * @param w 目标宽 
	    * @param h 目标高 
	    * @param per 百分比 
	    */ 
	    private static void  Tosmallerpic(String f,File file,String ext,String n,int w,int h,float per){ 
	            Image src; 
	            try { 
	                src  =  javax.imageio.ImageIO.read(file); //构造Image对象 
	 
	               String img_midname  =  f+n.substring(0,n.indexOf("."))+ext+n.substring(n.indexOf(".")); 
	               int old_w = src.getWidth(null); //得到源图宽 
	               int old_h = src.getHeight(null); 
	               int new_w = 0; 
	               int new_h = 0; //得到源图长 
	 
	               double w2 = (old_w*1.00)/(w*1.00); 
	               double h2 = (old_h*1.00)/(h*1.00); 
	 
	               //图片跟据长宽留白，成一个正方形图。 
	               BufferedImage oldpic; 
	               if(old_w>old_h) 
	               { 
	                   oldpic = new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
	               }else{if(old_w<old_h){ 
	                   oldpic = new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
	               }else{ 
	                    oldpic = new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
	               } 
	               } 
	                Graphics2D g  =  oldpic.createGraphics(); 
	                g.setColor(Color.white); 
	                if(old_w>old_h) 
	                { 
	                    g.fillRect(0, 0, old_w, old_w); 
	                    g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null); 
	                }else{ 
	                    if(old_w<old_h){ 
	                    g.fillRect(0,0,old_h,old_h); 
	                    g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null); 
	                    }else{ 
	                        //g.fillRect(0,0,old_h,old_h); 
	                        g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
	                    } 
	                }              
	                g.dispose(); 
	                src  =  oldpic; 
	                //图片调整为方形结束 
	               if(old_w>w) 
	               new_w = (int)Math.round(old_w/w2); 
	               else 
	                   new_w = old_w; 
	               if(old_h>h) 
	               new_h = (int)Math.round(old_h/h2);//计算新图长宽 
	               else 
	                   new_h = old_h; 
	               BufferedImage image_to_save  =  new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);        
	               image_to_save.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
	               FileOutputStream fos = new FileOutputStream(img_midname); //输出到文件流 
	                
	               //旧的使用 jpeg classes进行处理的方法
//	               JPEGImageEncoder encoder  =  JPEGCodec.createJPEGEncoder(fos); 
//	               JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image_to_save); 
	                /* 压缩质量 */ 
//	               jep.setQuality(per, true); 
//	               encoder.encode(image_to_save, jep); 
	                
	               //新的方法
	               saveAsJPEG(100, image_to_save, per, fos);
	                
	               fos.close(); 
	               } catch (IOException ex) { 
	                //Logger.getLogger(Img_Middle.class.getName()).log(Level.SEVERE, null, ex); 
	            } 
	    } 
	     
	   
	    /**
	     * 以JPEG编码保存图片
	     * @param dpi  分辨率
	     * @param image_to_save  要处理的图像图片
	     * @param JPEGcompression  压缩比
	     * @param fos 文件输出流
	     * @throws IOException
	     */
	    public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {
	          
	        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
	        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
	        imageWriter.setOutput(ios);
	        //and metadata
	        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
	         
	         
	        if(dpi !=  null && !dpi.equals("")){
	             
	             //old metadata
	            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
	            //jpegEncodeParam.setXDensity(dpi);
	            //jpegEncodeParam.setYDensity(dpi);
	      
	            //new metadata
	            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
	            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
	            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
	            jfif.setAttribute("Ydensity", Integer.toString(dpi));
	             
	        }
	      
	      
	        if(JPEGcompression >= 0 && JPEGcompression <= 1f){
	      
	            //old compression
	            //jpegEncodeParam.setQuality(JPEGcompression,false);
	      
	            // new Compression
	            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
	            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
	            jpegParams.setCompressionQuality(JPEGcompression);
	      
	        }
	      
	        //old write and clean
	        //jpegEncoder.encode(image_to_save, jpegEncodeParam);
	      
	        //new Write and clean up
	        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
	        ios.close();
	        imageWriter.dispose();
	      
	    }

}
