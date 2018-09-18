package com.buywhat.demo.service;

import com.buywhat.demo.bean.Game.Pokemon2;
import com.buywhat.demo.dao.Pokemon2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    Pokemon2Mapper pokemon2Mapper;

    /**
     * 战斗前初始化PM
     *
     * @return 返回1 4 7 号PM
     */
    @Override
    public Map findPmBeforeGame() {

        //初始的PM规定为1 4 7 号
        Map map = new HashMap();
        Pokemon2 pm1 = pokemon2Mapper.selectByPrimaryKey(1);
        Pokemon2 pm4 = pokemon2Mapper.selectByPrimaryKey(4);
        Pokemon2 pm7 = pokemon2Mapper.selectByPrimaryKey(7);
        map.put(1, pm1);
        map.put(4, pm4);
        map.put(7, pm7);


        return map;
    }
}
