package com.mmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mmall.domain.User;
import com.mmall.mapper.UserMapper;
import com.mmall.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 根据用户名称查询用户对象
	 */
	@Override
	public User findUserByUserName(String username) {
		return userMapper.findUserByUserName(username);
	}
	
	/**
	 * 分页查询用户
	 */
	@Override
	public List<User> selectUserList(int page, int pagesize) {
		PageHelper.startPage(page, pagesize);
		return userMapper.selectUserList();
	}

}
