package com.zmall.dao;

import com.zmall.pojo.PayInfo;
import com.zmall.pojo.PayInfoExample;

public interface PayInfoMapper {
    int deleteByExample(PayInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}