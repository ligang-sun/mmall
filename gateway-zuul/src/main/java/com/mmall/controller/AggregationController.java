package com.mmall.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Maps;
import com.mmall.domain.Order;
import com.mmall.service.AggregationService;

import rx.Observable;
import rx.Observer;

@RestController
public class AggregationController {
	
	 public static final Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);
	 
	  @Autowired
	  private AggregationService aggregationService;

	  @GetMapping("/aggregate/{id}")
	  public DeferredResult<HashMap<String, List<Order>>> aggregate(@PathVariable Long id) {
	    Observable<HashMap<String, List<Order>>> result = this.aggregateObservable(id);
	    return this.toDeferredResult(result);
	  }

	public Observable<HashMap<String, List<Order>>> aggregateObservable(Long id) {
	    // 合并两个或者多个Observables发射出的数据项，根据指定的函数变换它们
	    return Observable.zip(
	            this.aggregationService.selectOrderListByUserId(id),
	            this.aggregationService.selectOrderListByUserId2(id),
	            (orderList, userOrderList) -> {
	              HashMap<String, List<Order>> map = Maps.newHashMap();
	              map.put("orderList", orderList);
	              map.put("userOrderList", userOrderList);
	              return map;
	            }
	    );
	  }
	  
	  public DeferredResult<HashMap<String, List<Order>>> toDeferredResult(Observable<HashMap<String, List<Order>>> details) {
		    DeferredResult<HashMap<String, List<Order>>> result = new DeferredResult<>();
		    // 订阅
		    details.subscribe(new Observer<HashMap<String, List<Order>>>() {
		      @Override
		      public void onCompleted() {
		        LOGGER.info("完成...");
		      }

		      @Override
		      public void onError(Throwable throwable) {
		        LOGGER.error("发生错误...", throwable);
		      }

		      @Override
		      public void onNext(HashMap<String, List<Order>> movieDetails) {
		        result.setResult(movieDetails);
		      }
		    });
		    return result;
		  }
	 

}
