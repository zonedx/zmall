package com.zmall.dao;

import com.zmall.pojo.User;
import com.zmall.pojo.UserExample;

public interface UserMapper {
    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}