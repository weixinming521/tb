package com.travel.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

/**
 * 文件上传工具类
 * @author Administrator
 *
 */
public class FileUploadUtils {
	private static final Logger log = Logger.getLogger(FileUploadUtils.class);
	
	public static String GenerateImage(String imgStr ,HttpServletRequest request){
				
		String root = request.getSession().getServletContext().getRealPath("/");
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);//获取年份
	    int month=cal.get(Calendar.MONTH)+1;//获取月份
	    int day=cal.get(Calendar.DATE);//获取日 
	    /**文件路径**/
	    String path ="/upload/"+year+"/"+month+"/"+day+"/";
	    /**文件名**/
	    String fileName = IDUtils.genImageName();
	    //生成jpeg图片  
	  	String imgFilePath =root+path+fileName+".jpg";//新生成的图片
		
		//对字节数组字符串进行Base64解码并生成图片  
		 if (imgStr == null) //图像数据为空  
			 return null;  
		 BASE64Decoder decoder = new BASE64Decoder();  
		 try {  
			 //Base64解码  
			 byte[] b = decoder.decodeBuffer(imgStr);  
			 for(int i=0;i<b.length;++i) {  
				 if(b[i]<0){//调整异常数据  
					 b[i]+=256;  
				 }  
			 }  		
			 OutputStream out = new FileOutputStream(imgFilePath);      
			 out.write(b);
			 out.flush();  
			 out.close(); 
			 // 返回的是相对路径
			 return path+fileName+".jpg";  
	        }   
	        catch (Exception e)   
	        {  
	        	log.error("图片上传异常："+e.toString());
	            return null;  
	        }  
	}  
	public static String imgUpload(HttpServletRequest request ,MultipartFile imgFile ){
		
		String root = request.getSession().getServletContext().getRealPath("/");
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);//获取年份
	    int month=cal.get(Calendar.MONTH)+1;//获取月份
	    int day=cal.get(Calendar.DATE);//获取日 
	    /**文件路径**/
	    String path ="/upload/"+year+"/"+month+"/"+day+"/";
	    /**文件名**/
	    String fileName = IDUtils.genImageName();	    
		/**获取文件的后缀**/    
		String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
		
		// 判断文件夹是否存在
		File file = new File(root+path);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		//生成jpeg图片  
		String imgFilePath =root+path+fileName+suffix;//新生成的图片
		try {
		    	
			 OutputStream out = new FileOutputStream(imgFilePath);
			 
				out.write(imgFile.getBytes());
				out.flush();  
				out.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("图片上传异常："+e.toString());
			}
		 // 返回的是相对路径
		return path+fileName+suffix;    
		
	}

}
