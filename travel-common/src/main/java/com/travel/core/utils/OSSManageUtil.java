package com.travel.core.utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;  
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.travel.core.utils.IDUtils;
import com.travel.core.web.Constant;

import sun.misc.BASE64Decoder;  
    

  
/** 
 * 对OSS服务器进行上传删除等的处理 
 * @ClassName: OSSManageUtil  
 * @Description:   
 * 
 */  
public class OSSManageUtil { 
	
	public static final String endpoint = "oss-cn-hangzhou.aliyuncs.com";
	public static final String accessKeyId = "1lZwlPmNNNYDIz3x";
	public static final String accessKeySecret = "g2FjSOlqk2rowqOuUQrwUCkQdgBMDj";
	 /** 
     * 上传OSS服务器html
    * @Title: uploadFile
    * @Description:  
    * @param @param file 
    * @param @param remotePath 
    * @param @return 
    * @param @throws Exception    设定文件  
    * @return String    返回类型  
    * @throws 
     */  
    public static String uploadFile(String path,String pathname) throws Exception{ 
    	File file = new File(path);
    //	return	uploadFile(file,".html");
    	OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);    
		ObjectMetadata objectMeta = new ObjectMetadata();
	    objectMeta.setContentLength(file.length());	        
	    objectMeta.setContentType(contentType(".html"));
	    InputStream input = new FileInputStream(file);
	    client.putObject(Constant.bucketName, pathname, input, objectMeta);				
		client.shutdown();
    	return pathname;

    }
 
    
    /** 
     * 上传OSS服务器文件 
    * @Title: uploadFile
    * @Description:  
    * @param @param file 
    * @param @param remotePath 
    * @param @return 
    * @param @throws Exception    设定文件  
    * @return String    返回类型  
    * @throws 
     */  
    public static String uploadFile(MultipartFile imgFile) throws Exception{
    	
    	
    	/**获取文件的后缀**/    
		String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".")); 
		  
		CommonsMultipartFile commonsMultipartFile= (CommonsMultipartFile)imgFile; 

		InputStream inputStream = commonsMultipartFile.getInputStream();
		
		
    	OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	

    	String date = DateUtil.format(new Date(), "yyyy/MM/dd/");
    	String fileName = IDUtils.genImageName();
    	    	     
    	String path = date+fileName+suffix;
    				
		ObjectMetadata objectMeta = new ObjectMetadata();
	    objectMeta.setContentType(contentType(suffix));
	    client.putObject(Constant.bucketName, path, inputStream, objectMeta);
					
		client.shutdown();
		
		BufferedImage buff = ImageIO.read(commonsMultipartFile.getInputStream());
		int width = buff.getWidth();//得到图片的宽度
		int height = buff.getHeight();  //得到图片的高度
		
    	return path+"?"+width+"_"+height;
    }
 
  
    /**
     * app文件上传(Base64)
     * @param file
     * @param suffix
     * @return
     * @throws Exception
     */
    public static String appUploadFile(String imgStr , String suffix) throws Exception{ 
        
    	OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    	String date = DateUtil.format(new Date(), "yyyy/MM/dd/");
    	String fileName = IDUtils.genImageName();
    	     
    	String path = date+fileName+suffix;
    				
		ObjectMetadata objectMeta = new ObjectMetadata();
	    // 可以在metadata中标记文件类型
		objectMeta.setContentType(contentType(suffix));
	        
	    BASE64Decoder decoder = new BASE64Decoder();  
	    //Base64解码  
	    byte[] b = decoder.decodeBuffer(imgStr);  
	    for(int i=0;i<b.length;++i) {  
	    	if(b[i]<0){//调整异常数据  
	    		b[i]+=256;  
	    	}  
	    }						 
		InputStream input = new ByteArrayInputStream(b);
	        	        
	    client.putObject(Constant.bucketName, path, input, objectMeta);
					
		client.shutdown();
		
    	return path;
    } 
    
    /**
     * 多文件上传
     * @param request
     * @param response
     * @return 返回图片地址使用“，”分割
     * @throws Exception
     */
    public static String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	  
    	StringBuilder sb = new StringBuilder();
    	MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
		//从MultipartHttpServletRequest取出图片
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
							
			String path = OSSManageUtil.uploadFile(pic);
			
			// url 
			String url = Constant.IMG_PATH+ path;
			
			sb.append(url).append(",");
		}
    	return sb.toString();    
    }
    
    /** 
    * Description: 判断OSS服务文件上传时文件的contentType 
    * @Version1.0 
    * @param FilenameExtension 文件后缀 
    * @return String  
    */  
    public static String contentType(String FilenameExtension){  
       if(FilenameExtension.equals(".BMP")||FilenameExtension.equals(".bmp")){return "image/bmp";}  
       if(FilenameExtension.equals(".GIF")||FilenameExtension.equals(".gif")){return "image/gif";}  
       if(FilenameExtension.equals(".JPEG")||FilenameExtension.equals(".jpeg")||  
          FilenameExtension.equals(".JPG")||FilenameExtension.equals(".jpg")||     
          FilenameExtension.equals(".PNG")||FilenameExtension.equals(".png")){return "image/jpeg";}  
       if(FilenameExtension.equals(".HTML")||FilenameExtension.equals(".html")){return "text/html";}  
       if(FilenameExtension.equals(".TXT")||FilenameExtension.equals(".txt")){return "text/plain";}  
       if(FilenameExtension.equals(".VSD")||FilenameExtension.equals(".vsd")){return "application/vnd.visio";}  
       if(FilenameExtension.equals(".PPTX")||FilenameExtension.equals(".pptx")||  
           FilenameExtension.equals(".PPT")||FilenameExtension.equals(".ppt")){return "application/vnd.ms-powerpoint";}  
       if(FilenameExtension.equals(".DOCX")||FilenameExtension.equals(".docx")||  
           FilenameExtension.equals(".DOC")||FilenameExtension.equals(".doc")){return "application/msword";}  
       if(FilenameExtension.equals(".XML")||FilenameExtension.equals(".xml")){return "text/xml";}  
       return "text/html";  
    } 
 
}  