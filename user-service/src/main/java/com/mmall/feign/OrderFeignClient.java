package com.mmall.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.components.OrderFeignClientFallback;
import com.mmall.domain.Order;
/**
 * 使用FeignClient的fallback属性指定的回退类
 * 
 * @author ligang.sun
 *
 */
@FeignClient(name = "user-order", fallback = OrderFeignClientFallback.class)
//@RequestMapping(value = "order")
public interface OrderFeignClient {

	/**
	 * 分页查询所有的订单
	 * 
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/order/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<Order> selectOrderList(@RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize);

	/**
	 * 分页查询用户的订单
	 * 
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/order/{userId}/list", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<Order> selectOrderListByUserId(@PathVariable(name = "userId") int userId,
	        @RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pagesize", defaultValue = "10") int pagesize);
}
