package com.mmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@RequestMapping(value = "{id}/order3", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId3(@PathVariable(name = "id") int id) {
		List<Order> orderList = this.orderFeignClient.selectOrderListByUserId(id,1,10);
		return orderList;
	}

	/**
	 * 获取用户的订单--通过服务名调用order-service
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}/order2", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<Order> findOrderListByUserId2(@PathVariable(name = "id") int id) {
		Object obj = restTemplate.getForObject("http://USER-ORDER/order/{id}/list", Object.class, id);
		List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
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
		Object obj = restTemplate.getForObject("http://localhost:8010/order/{id}/list", Object.class, id);
		List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
		return orderList;
	}

	/**
	 * 根据用户名称查询用户对象
	 * 
	 * @param username
	 * @return
	 */
<<<<<<< HEAD
	@RequestMapping(value = "user/{username}", method = RequestMethod.GET)
	public User findUserByUserName(@PathVariable(value="username", required = true) String username){
=======
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public User findUserByUserName(@PathVariable(name = "username", required = true) String username) {
>>>>>>> 9cc1e5fea141e8d73e719bad1821c1d547a06bc2
		return userService.findUserByUserName(username);
	}

	/**
	 * 分页查询用户
	 * 
	 * @return
	 */
<<<<<<< HEAD
	@RequestMapping(value = "user/list", method = RequestMethod.GET)
	public List<User> selectUserList(@RequestParam(name = "page", defaultValue = "1") int page,@RequestParam(name = "pagesize", defaultValue = "10") int pagesize){
		return userService.selectUserList(page,pagesize);
=======
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<User> selectUserList(@RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize) {
		return userService.selectUserList(page, pagesize);
>>>>>>> 9cc1e5fea141e8d73e719bad1821c1d547a06bc2
	}

}
