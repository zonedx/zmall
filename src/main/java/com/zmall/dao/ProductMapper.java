package com.zmall.dao;

import com.zmall.pojo.Product;
import com.zmall.pojo.ProductExample;

public interface ProductMapper {
    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}