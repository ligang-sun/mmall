package com.mmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.mmall.domain.Order;
import com.mmall.mapper.OrderMapper;
import com.mmall.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 分页查询订单
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<Order> selectOrderList(int page, int pagesize) {
		
		PageHelper.startPage(page, pagesize);
		return orderMapper.selectOrderList();
	}

	/**
	 * 分页查询用户的订单
	 * @param userId
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<Order> selectOrderListByUserId(int userId, int page, int pagesize) {
		PageHelper.startPage(page, pagesize);
		return orderMapper.selectOrderListByUserId(userId);
	}

}
