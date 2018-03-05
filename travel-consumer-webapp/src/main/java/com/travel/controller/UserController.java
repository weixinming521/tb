package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.bean.user.User;
import com.travel.core.utils.TravelResult;
import com.travel.service.UserService;

/**
 * 用户信息管理
 * @author Administrator
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 通过用户id获取用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/user/queryUser")
	@ResponseBody
	public Object  queryUser(Long userId){
		if(userId !=null){			
			User user = userService.queryUserById(userId);
			if(user!=null){
				return TravelResult.ok(user);
			}else{
				return TravelResult.build(1001, "当前用户不存在!");
			}
		}else{
			return TravelResult.build(1000, "参数为空");
		}		
	}
}
