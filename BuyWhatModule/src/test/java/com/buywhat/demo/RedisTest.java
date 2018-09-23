package com.buywhat.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class RedisTest {

    @Test
    public void testRedis1() {

        Jedis jedis = new Jedis();
        Long sadd = jedis.sadd("1","1");
        System.out.println("sadd = " + sadd);

    }
}
