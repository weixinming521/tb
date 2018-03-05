package com.travel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账单管理
 * @author Administrator
 *
 */
@Controller
public class BillController {

	
	/**
	 * 用户账单明细列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/incomeList")
	public String  incomeList(HttpServletRequest request, Model model){
		return "/income/incomeList";		
	}
	/**
	 * 用户账单明细详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/incomeDetails")
	public String  incomeDetails(HttpServletRequest request, Model model){
		return "/income/incomeDetails";		
	}

}
