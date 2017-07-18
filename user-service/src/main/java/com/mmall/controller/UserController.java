package com.mmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmall.domain.User;
import com.mmall.service.UserService;

@RestController(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * 根据用户名称查询用户对象
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public User findUserByUserName(@PathVariable(value="username", required = true) String username){
		return userService.findUserByUserName(username);
	}
	
	/**
	 * 分页查询用户
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> selectUserList(@RequestParam(name = "page", defaultValue = "1") int page,@RequestParam(name = "pagesize", defaultValue = "10") int pagesize){
		return userService.selectUserList(page,pagesize);
	}

}
