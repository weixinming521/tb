package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.bean.user.User;
import com.travel.core.WebConstant;
import com.travel.core.utils.AliyunSmsUtils;
import com.travel.core.utils.TravelResult;
import com.travel.service.RedisService;
import com.travel.service.UserService;

/**
 * 手机短信验证码发送类
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/sms")
public class SMSController {
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	/**
	 * 使用手机号登陆验证码/找回手机密码
	 * @param telephone
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginCheck")
	public Object  loginCheck(String telephone ){
		
		User user = userService.queryUserByPhone(telephone);
		if(user!=null){
			String fourRandom = AliyunSmsUtils.getFourRandom();
			redisService.set(WebConstant.SMS_CODE+telephone, fourRandom);
			AliyunSmsUtils.sendSmsCheck("SMS_104720032", telephone, fourRandom);
			return TravelResult.ok();
		}else{
			return TravelResult.build(1001, "用户不存在!");
		}
		
	}
	
	/**
	 * 手机号注册验证码
	 * @param telephone
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/registCheck")
	public Object  registCheck(String telephone ){
		
		User user = userService.queryUserByPhone(telephone);
		if(user==null){
			String fourRandom = AliyunSmsUtils.getFourRandom();
			redisService.set(WebConstant.SMS_CODE+telephone, fourRandom);	
			AliyunSmsUtils.sendSmsCheck("SMS_104720032", telephone, fourRandom);
			return TravelResult.ok();
		}else{
			return TravelResult.build(1002, "用户已存在!");
		}
	}
	
	/**
	 * 验证验证码是否正确
	 * @param telephone
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/checkCode")
	public Object  registCheck(String telephone ,String code){
		
		if(telephone!=null && code !=null){
			String string = redisService.get(WebConstant.SMS_CODE+telephone);
			if(string !=null && string.equals(code)){				
				return TravelResult.ok();
			}else{
				return TravelResult.build(1004, "验证码错误!");
			}
		}else{
			return TravelResult.build(1003, "手机号或验证码不能为空!");
		}
	}

}
