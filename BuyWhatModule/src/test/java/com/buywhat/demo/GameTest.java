package com.buywhat.demo;

import com.buywhat.demo.bean.game.Pokemon2;
import com.buywhat.demo.dao.Pokemon2Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class GameTest {

    @Autowired
    Pokemon2Mapper mapper;
    @Test
    public void test123(){

        Pokemon2 pokemon2 = mapper.selectByPrimaryKey(1);
        System.out.println("pokemon2 = " + pokemon2);
    }
}
