package com.fosu.gmall;

import com.fosu.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageServiceApplicationTests {
    @Autowired
    RedisUtil redisUtil;
    @Test
    public void Test(){
        Jedis jedis = redisUtil.getJedis();
        System.out.println(jedis);
    }
}
