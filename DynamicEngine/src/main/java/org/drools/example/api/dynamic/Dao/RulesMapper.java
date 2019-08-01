package org.drools.example.api.dynamic.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.drools.example.api.model.Rules;

import java.util.List;

/**
 * @author 雷新宇
 * @version 1.0
 * @date 2019/7/31 14:54
 */
@Mapper
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