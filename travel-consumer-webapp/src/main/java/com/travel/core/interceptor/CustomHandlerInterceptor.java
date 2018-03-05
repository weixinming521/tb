package com.travel.core.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.travel.service.SessionProvider;


/**
 * 用户登录拦截器
 * @author Administrator
 *
 */
public class CustomHandlerInterceptor implements HandlerInterceptor{

	@Autowired
	private SessionProvider sessionProvider;

	//方法前
	@SuppressWarnings("deprecation")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();	
		if(requestURI.startsWith("/user/")){
			//必须登陆
			Long userId = sessionProvider.getUserId(request, response);
			if(null != userId){
				request.setAttribute("userId", userId);
			}else{
				//重定向登陆页面
				StringBuffer requestURL = request.getRequestURL();
				String queryString = request.getQueryString();
				requestURL.append("?").append(queryString);
				String url = URLEncoder.encode(requestURL.toString(), "UTF-8");
				response.sendRedirect("?returnUrl="+url);				
				return false;
			}
		}
		//放行
		return true;
	}
	//方法后
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	//页面渲染后
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
