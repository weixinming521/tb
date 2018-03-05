package com.travel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户认证
 * @author Administrator
 *
 */
@Controller
public class UserAuthController {

	
	/**
	 * 个人用户认证界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/userapprove")
	public String  userapprove(HttpServletRequest request, Model model){
		return "approve/userapprove";		
	}
	/**
	 * 企业认证界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/firmapprove")
	public String  firmapprove(HttpServletRequest request, Model model){
		return "approve/firmapprove";		
	}

}
