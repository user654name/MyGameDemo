package com.buywhat.demo.service;

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
}
