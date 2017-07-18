package com.mmall.mapper;

import java.util.List;

import com.mmall.domain.User;

public interface UserMapper {

	/**
	 * 根据用户名称查询用户对象
	 * @param username
	 * @return
	 */
	User findUserByUserName(String username);

	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> selectUserList();

}
