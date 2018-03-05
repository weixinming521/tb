package com.travel.core.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtils {
	
	/**
	 * 获取csessionid 如果不存在则重新生成，存在直接获取
	 * @param request
	 * @param response
	 * @return 返回sessionid
	 */
	public static String getSessionID(HttpServletRequest request,HttpServletResponse response){
		//取Cookie的中的csessionid
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if("CSESSIONID".equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		//没有生成
		String csessionid = UUID.randomUUID().toString().replace("-", "");
		//保存生成的csessionid 到 Cookie
		Cookie cookie = new Cookie("CSESSIONID", csessionid);
		//存活时间  关闭浏览器消失   -1   
		cookie.setMaxAge(7*24*60*60*60);
		//设置路径    
		cookie.setPath("/");
		// 设置信任路径
		//cookie.setDomain("");
		response.addCookie(cookie);
			
		return  csessionid;
			
	}
	
	

}
