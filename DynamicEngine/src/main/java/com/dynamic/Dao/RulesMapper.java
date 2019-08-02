package com.dynamic.Dao;

import com.dynamic.Model.Rules;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @date 2019/7/31 14:54
 */
//@Mapper
public interface RulesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rules record);

    int insertSelective(Rules record);

    Rules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rules record);

    int updateByPrimaryKeyWithBLOBs(Rules record);

    int updateByPrimaryKey(Rules record);

    List<Rules> selectAll();
}