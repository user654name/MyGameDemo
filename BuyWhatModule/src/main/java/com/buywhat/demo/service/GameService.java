package com.buywhat.demo.service;

import java.util.Map;

public interface GameService {

    /**
     * 战斗前初始化PM
     *
     * @return 返回1 4 7 号PM
     */
    Map findPmBeforeGame();
}
