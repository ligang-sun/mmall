package com.mmall.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.mmall.domain.Order;
import com.mmall.service.AggregationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;

@Service
public class AggregationServiceImpl implements AggregationService{
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(fallbackMethod = "fallback")
	public Observable<List<Order>> selectOrderListByUserId(Long id) {
		// 创建一个被观察者
	    return Observable.create(observer -> {
	      // 请求用户微服务的/{id}端点
	      Object obj = restTemplate.getForObject("http://user-order/order/{id}/list",Object.class,id);
	      List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
	      observer.onNext(orderList);
	      observer.onCompleted();
	    });
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallback")
	public Observable<List<Order>> selectOrderListByUserId2(Long id) {
		// 创建一个被观察者
	    return Observable.create(observer -> {
	      // 请求用户微服务的/{id}端点
	      Object obj = restTemplate.getForObject("http://user-service/user/{id}/order2",Object.class,id);
	      List<Order> orderList = JSON.parseArray(JSON.toJSON(obj).toString(), Order.class);
	      observer.onNext(orderList);
	      observer.onCompleted();
	    });
	}
	
	  public List<Order> fallback(Long id) {
		  	Order order = new Order();
		  	order.setId(-1);
		    return Arrays.asList(order);
		  }

}
