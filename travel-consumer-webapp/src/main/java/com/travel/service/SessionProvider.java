package com.travel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.core.utils.RequestUtils;

/**
 * session缓存reids中
 * @author Administrator
 *
 */
@Service(value = "sessionProvider")
public class SessionProvider {

	@Autowired
	private RedisService redisService;
	/**
	 * 通过session获取当前客户端用户id
	 * @param request
	 * @param response
	 * @return
	 */
	public void putSession(HttpServletRequest request,HttpServletResponse response , Long userId){
		String sessionID = RequestUtils.getSessionID(request, response);
		redisService.set("session:"+sessionID, userId.toString(), 60*60*24);
	}
	
	/**
	 * 通过session获取当前客户端用户id
	 * @param request
	 * @param response
	 * @return
	 */
	public Long getUserId(HttpServletRequest request,HttpServletResponse response){
		String sessionID = RequestUtils.getSessionID(request, response);
		String userId = redisService.get("session:"+sessionID);
		if(userId!=null){
			redisService.expire("session:"+sessionID, 60*60*24);
			return Long.parseLong(userId);
		}else{
			return null;
		}
	}
}
