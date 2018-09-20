package com.buywhat.demo.service;

import com.buywhat.demo.bean.game.TeamGameRecord;

import java.util.Map;

public interface GameService {

    /**
     * 战斗前初始化PM
     *
     * @return 返回1 4 7 号PM
     */
    Map findPmBeforeGame();

    /**
     * 精灵们进行对战
     *
     * @param comPmId    电脑选择的精灵
     * @param playerPmId 玩家选择的精灵
     * @return 战斗结果信息
     */
    Map hitEachOther(Integer comPmId, Integer playerPmId);

    /**
     * @param player1Id 玩家1 id
     * @param player2Id   玩家2 id 通常是电脑
     * @param playerWin 玩家1 是否获胜
     * @return 战绩信息更新 成功
     */
    boolean updateTeamGameRecord(Integer player1Id, Integer player2Id, Integer playerWin);

    /**
     * 根据玩家ID查新对应的战绩
     *
     * @param playerId
     * @return
     */
    TeamGameRecord findTeamGameRecordByUserId(Integer playerId);
}
