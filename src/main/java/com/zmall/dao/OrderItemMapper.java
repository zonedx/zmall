package com.zmall.dao;

import com.zmall.pojo.OrderItem;
import com.zmall.pojo.OrderItemExample;

public interface OrderItemMapper {
    int deleteByExample(OrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}