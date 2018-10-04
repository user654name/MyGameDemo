package com.buywhat.demo;

import com.buywhat.demo.bean.game.Pokemon2;
import com.buywhat.demo.bean.game.TeamGameRecord;
import com.buywhat.demo.dao.Pokemon2Mapper;
import com.buywhat.demo.dao.TeamGameRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class GameTest {

    @Autowired
    Pokemon2Mapper mapper;

    @Autowired
    TeamGameRecordMapper teamGameRecordMapper;


    /**
     * 2018年9月20日 23:21:58
     */
    @Test
    public void test3() {

        List<TeamGameRecord> teamGameRecordsteamGameRecords = teamGameRecordMapper.selectTop10ByWin();
        System.out.println("teamGameRecordsteamGameRecords = " + teamGameRecordsteamGameRecords);


    }



    @Test
    public void test1() {

        Pokemon2 pokemon2 = mapper.selectByPrimaryKey(1);
        System.out.println("pokemon2 = " + pokemon2);
    }

    @Test
    public void test2() {

        TeamGameRecord playerRecord = new TeamGameRecord();

        Date timeNow = new Date();

        playerRecord.setTotal(1);
        playerRecord.setFirstGameDate(timeNow);//2018年9月20日 14:32:22出错
        playerRecord.setLastGameDate(timeNow);
        playerRecord.setUserId(-1);

        if (true) {//如果玩家是胜者
            playerRecord.setWin(1);//增加一场胜场
        } else {//这个玩家第一局就输了
            playerRecord.setWin(0);//胜场为0
        }

        //插入数据
        int insert = teamGameRecordMapper.insert(playerRecord);
        System.out.println("insert = " + insert);
    }


}
