package com.mmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mmall.domain.Order;
import com.mmall.domain.User;
import com.mmall.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

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
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	/**
	 * 查询订单中的用户信息
	 */
	@HystrixCommand(fallbackMethod = "findUserByUserIdFallback", 
			commandProperties={
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
			@HystrixProperty(name ="execution.isolation.strategy", value = "SEMAPHORE")
			
	},		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "1"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
	}
	)
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public User findUserByUserId(@PathVariable(name="username") String username){
		
		return restTemplate.getForObject("http://USER-SERVICE/user/{username}", User.class, username);
		
	}
	
	public User findUserByUserIdFallback(@PathVariable(name="username") String username){
		User user = new User();
		user.setId(1L);
		user.setUsername("默认用户");
		
		return user;
		
	}
	
	
	

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
