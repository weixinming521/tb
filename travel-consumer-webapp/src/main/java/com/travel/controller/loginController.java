package com.travel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.bean.user.User;
import com.travel.core.WebConstant;
import com.travel.core.utils.MD5Utils;
import com.travel.core.utils.TravelResult;
import com.travel.service.RedisService;
import com.travel.service.SessionProvider;
import com.travel.service.UserService;

@Controller
public class loginController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SessionProvider sessionProvider;
	/**
	 * web登录界面跳转
	 * @return
	 */
	@RequestMapping(value="/")
	public String  indexLogin(){
		return "login/loginSign";
	}
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param telephone
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object  login(HttpServletRequest request,HttpServletResponse response ,String telephone ,String password ){
		if(telephone!=null && password!=null){
			User user = userService.queryUserByPhone(telephone);
			if(user!=null){
				password = MD5Utils.encodePasword(password);
				if(password.equals(user.getPassword())){
					sessionProvider.putSession(request, response, user.getId());
					return TravelResult.ok();
				}else{
					return TravelResult.build(1003, "手机号/密码错误!");
				}
			}else{
				return TravelResult.build(1001, "当前用户不存在!");
			}
		}else{
			return TravelResult.build(1000, "手机号/密码不能为空!");
		}
	}
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @param telephone
	 * @param password
	 * @param checkCode
	 * @return
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object  register(HttpServletRequest request,HttpServletResponse response ,String telephone ,String password , String checkCode ){		
		if(telephone!=null && password!=null && checkCode!=null){
			String oldCheckCode = redisService.get(WebConstant.SMS_CODE+telephone);
			if(checkCode.equals(oldCheckCode)){
				User user = userService.queryUserByPhone(telephone);
				if(user==null){
					User u = new User();
					u.setPhoneNum(telephone);
					u.setPassword(MD5Utils.encodePasword(password));
					Long userId = userService.saveUser(u);
					sessionProvider.putSession(request, response, userId);
					return TravelResult.ok();
				}else{
					return TravelResult.build(1002, "当前用户已存在!");
				}
			}else{
				return TravelResult.build(1004, "验证码错误，请重新请求!");
			}
		}else{
			return TravelResult.build(1000, "手机号/验证码/密码不能为空!");
		}
	}
	/**
	 * 用户找回密码
	 * @param request
	 * @param response
	 * @param telephone 手机号
	 * @param checkCode 验证码
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="/updatePassword")
	@ResponseBody
	public Object  updatePassword(HttpServletRequest request,HttpServletResponse response ,String telephone , String checkCode,String password ){		
		if(telephone!=null && password!=null && checkCode!=null){
			String oldCheckCode = redisService.get(WebConstant.SMS_CODE+telephone);
			if(checkCode.equals(oldCheckCode)){
				User user = userService.queryUserByPhone(telephone);
				if(user!=null){
					user.setPassword(MD5Utils.encodePasword(password));
					userService.updateUser(user);
					sessionProvider.putSession(request, response, user.getId());
					return TravelResult.ok();
				}else{
					return TravelResult.build(1001, "当前用户不存在!");
				}
			}else{
				return TravelResult.build(1004, "验证码错误，请重新请求!");
			}
		}else{
			return TravelResult.build(1000, "手机号/验证码/密码不能为空!");
		}
	}
	/**
	 * 修改用户密码界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/passwordBack")
	public String  userInfo(HttpServletRequest request, Model model){
		return "login/passwordBack";		
	}
	

}
