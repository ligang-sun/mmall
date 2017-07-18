package com.mmall.service;

import java.util.List;

import com.mmall.domain.User;

public interface UserService {

	/**
	 * 根据用户名查询用户对象
	 * @param username
	 * @return
	 */
	User findUserByUserName(String username);

	/**
	 * 分页查询用户
	 * @param page
	 * @param pagesize
	 * @return
	 */
	List<User> selectUserList(int page, int pagesize);

}
