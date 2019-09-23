package com.zmall.dao;

import com.zmall.pojo.OrderItem;
import com.zmall.pojo.OrderItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByExample(OrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderNoUserId(@Param("orderNo")Long orderNo,@Param("userId")Integer userId);

    List<OrderItem> getByOrderNo(Long orderNo);

    void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);

}