package com.travel.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.travel.bean.user.User;


@FeignClient(name="provider-user")
public interface UserService {
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/user/saveUser")
	public Long saveUser(User user);
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/updateUser")
	public Integer updateUser(User user);
	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/query/{id}")
	public User queryUserById(@RequestParam("id") Long id);
	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/queryPhone/{phone}")
	public User queryUserByPhone(@RequestParam("phone") String phone);
	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/list")
	public PageInfo<User> userList(User user , @RequestParam("pageNo") Integer pageNo , @RequestParam("order") String order);
}
