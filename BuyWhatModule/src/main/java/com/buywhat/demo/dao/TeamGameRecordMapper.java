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

    /**
     * 根据玩家的用户id查找战绩信息
     *
     * @param playerId 玩家的用户id
     * @return 这个玩家的战绩信息
     */
    TeamGameRecord selectByUserId(Integer playerId);
}