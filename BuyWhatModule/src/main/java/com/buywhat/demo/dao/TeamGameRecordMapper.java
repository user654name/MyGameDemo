package com.buywhat.demo.dao;

import com.buywhat.demo.bean.game.TeamGameRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TeamGameRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamGameRecord record);

    int insertSelective(TeamGameRecord record);

    TeamGameRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamGameRecord record);

    int updateByPrimaryKey(TeamGameRecord record);
}