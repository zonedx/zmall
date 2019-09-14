package com.zmall.dao;

import com.zmall.pojo.Cart;
import com.zmall.pojo.CartExample;

public interface CartMapper {
    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}