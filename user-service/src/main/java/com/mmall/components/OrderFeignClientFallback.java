package com.mmall.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mmall.domain.Order;
import com.mmall.feign.OrderFeignClient;
/**
 * OrderFeignClient的回退类 实现Feign Client的接口
 * 
 * 
 * @author ligang.sun
 *
 */
@Component
public class OrderFeignClientFallback implements OrderFeignClient{

	@Override
	public List<Order> selectOrderList(int page, int pagesize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> selectOrderListByUserId(int userId, int page, int pagesize) {
		List<Order> orderList = new ArrayList<>();
		Order order = new Order();
		order.setId(1);
		order.setUserId(1);
		order.setCreateTime(new Date());
		orderList.add(order);
		return orderList;
	}

}
