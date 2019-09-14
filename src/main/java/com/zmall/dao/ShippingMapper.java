package com.zmall.dao;

import com.zmall.pojo.Shipping;
import com.zmall.pojo.ShippingExample;

public interface ShippingMapper {
    int deleteByExample(ShippingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}