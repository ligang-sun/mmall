package com.mmall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.mmall.domain.Order;
import com.mmall.domain.User;
import com.mmall.feign.OrderFeignClient;
import com.mmall.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderFeignClient orderFeignClient;

	/**
	 * 获取用户的订单--Feign通过服务名调用order-service 
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value = "{id}/order4", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId4(@PathVariable(name = "id") int id) {
		List<Order> orderList = this.orderFeignClient.selectOrderListByUserId(id, 1, 10);
		return orderList;
	}

	

	/**
	 * 获取用户的订单--Feign通过服务名调用order-service
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}/order3", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId3(@PathVariable(name = "id") int id) {
		List<Order> orderList = this.orderFeignClient.selectOrderListByUserId(id, 1, 10);
		return orderList;
	}

	/**
	 * 获取用户的订单--通过服务名调用order-service  -- 添加Hystrix的机制
	 * 
	 * @param id
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "findOrderListByUserId2Fallbask", 
			commandProperties={
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
			@HystrixProperty(name ="execution.isolation.strategy", value = "SEMAPHORE")
			
	},		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "1"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
	}
	)
	@RequestMapping(value = "{id}/order2", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId2(@PathVariable(name = "id") int id) {
		Object obj = restTemplate.getForObject("http://USER-ORDER/order/{id}/list", Object.class, id);
		List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
		return orderList;
	}
	
	
	/**
	 * fallback访问方法
	 * 
	 * @param id
	 * @return
	 */
	public List<Order> findOrderListByUserId2Fallbask(@PathVariable(name = "id") int id) {
		List<Order> orderList = new ArrayList<>();
		Order order = new Order();
		order.setId(1);
		order.setUserId(1);
		order.setCreateTime(new Date());
		orderList.add(order);
		return orderList;
	}

	/**
	 * 获取用户的订单--通过具体ip调用order-service
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}/order", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId(@PathVariable(name = "id") int id) {
		/*
		 * 说明：给restTemplate加上@LoadBalanced后，这个使用localhost或者使用127.0.0.1的调用方式就不行了
		 * 报错：No instances available for 127.0.0.1
		 */
		Object obj = restTemplate.getForObject("http://127.0.0.1:8010/order/{id}/list", Object.class, id);
		List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
		return orderList;
	}

	/**
	 * 根据用户名称查询用户对象
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public User findUserByUserName(@PathVariable(name = "username", required = true) String username) {
		return userService.findUserByUserName(username);
	}

	/**
	 * 分页查询用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<User> selectUserList(@RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize) {
		return userService.selectUserList(page, pagesize);
	}

}
