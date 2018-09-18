package com.buywhat.demo.dao;

import com.buywhat.demo.bean.Game.Pokemon2;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface Pokemon2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pokemon2 record);

    int insertSelective(Pokemon2 record);

    Pokemon2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pokemon2 record);

    int updateByPrimaryKey(Pokemon2 record);
}