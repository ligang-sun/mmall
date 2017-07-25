package com.mmall.service;

import java.util.List;

import com.mmall.domain.Order;

import rx.Observable;

public interface AggregationService {

	Observable<List<Order>> selectOrderListByUserId(Long id);

	Observable<List<Order>> selectOrderListByUserId2(Long id);

}
