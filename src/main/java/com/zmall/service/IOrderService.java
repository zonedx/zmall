package com.zmall.service;

import com.zmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    ServerResponse create(Integer userId,Integer shippingId);

    ServerResponse<String> cancel(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

}
