package com.zmall.dao;

import com.zmall.pojo.Category;
import com.zmall.pojo.CategoryExample;

import java.util.List;

public interface CategoryMapper {
    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}