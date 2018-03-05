package com.travel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.bean.user.User;
import com.travel.service.UserService;

/**
 * 用户账户系统
 * @author Administrator
 *
 */
@Controller
public class AccountController {


	
	/**
	 * 用户账户首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account")
	public String  account(HttpServletRequest request, Model model){
		return "approve/approve";		
	}
	
	/**
	 * 用户账户首页/认证后的首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account_auth")
	public String  account_auth(HttpServletRequest request, Model model){
		return "/moneyBag/redMoneyBag";		
	}
	
	/**
	 * 充值
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/addMoney")
	public String  addMoney(HttpServletRequest request, Model model){
		return "/moneyBag/payMoney";		
	}
	
	/**
	 * 账户-忘记密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/resetPassword")
	public String  resetPassword(HttpServletRequest request, Model model){
		return "/moneyBag/resetPassword";		
	}
	/**
	 * 添加银行卡
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/addBank")
	public String  addBank(HttpServletRequest request, Model model){
		return "/bankBound/bankBound";		
	}
	
	/**
	 * 添加银行卡
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/addBankResult")
	public String  addBankResult(HttpServletRequest request, Model model){
		return "/bankBound/bankResult";		
	}
	/**
	 * 提现
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/getMoney")
	public String  getMoney(HttpServletRequest request, Model model){
		return "/getMoney/getMoney";		
	}
	/**
	 * 转账
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/transferMoney")
	public String  transferMoney(HttpServletRequest request, Model model){
		return "/zhuanzhang/transferMoney";		
	}
	
	/**
	 * 积分
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/account/integral")
	public String  integral(HttpServletRequest request, Model model){
		return "/credits/credits";		
	}

}
