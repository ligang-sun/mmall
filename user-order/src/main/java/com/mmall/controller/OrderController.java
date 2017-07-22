package com.mmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmall.domain.Order;
import com.mmall.service.OrderService;

/**
 * 订单Controller
 * 
 * @author ligang.sun
 *
 */

@RestController
@RequestMapping(value = "order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 分页查询所有的订单
	 * 
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> selectOrderList(@RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize) {

		return orderService.selectOrderList(page, pagesize);
	}

	/**
	 * 分页查询用户的订单
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "{userId}/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> selectOrderListByUserId(@PathVariable(name="userId") int userId,@RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize) {

		return orderService.selectOrderListByUserId(userId,page, pagesize);
	}

}