package com.mmall.service;

import java.util.List;

import com.mmall.domain.Order;

public interface OrderService {

	/**
	 * 分页查询订单
	 * @param page
	 * @param pagesize
	 * @return
	 */
	List<Order> selectOrderList(int page, int pagesize);

	/**
	 * 分页查询用户的订单
	 * @param userId
	 * @param page
	 * @param pagesize
	 * @return
	 */
	List<Order> selectOrderListByUserId(int userId, int page, int pagesize);

}
