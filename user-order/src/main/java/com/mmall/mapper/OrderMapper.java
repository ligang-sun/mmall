package com.mmall.mapper;

import java.util.List;

import com.mmall.domain.Order;

public interface OrderMapper {

	/**
	 * 查询所有的订单
	 * @return
	 */
	List<Order> selectOrderList();

	/**
	 * 查询用户的所有订单
	 * @param userId
	 * @return
	 */
	List<Order> selectOrderListByUserId(int userId);

}
