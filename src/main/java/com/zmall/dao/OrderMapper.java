package com.zmall.dao;

import com.zmall.pojo.Order;
import com.zmall.pojo.OrderExample;

public interface OrderMapper {
    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}