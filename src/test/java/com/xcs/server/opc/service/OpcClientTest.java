package com.xcs.server.opc.service;

import com.xcs.server.opc.memory.impl.RedisDataMemory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpcClientTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisDataMemory redisDataMemory;

    @Test
    public void start() throws InterruptedException {
        OpcClient client = new OpcClient();
        client.start();
        Thread.sleep(5000);
    }

    @Test
    public void testGet(){
        String variant = redisTemplate.opsForValue().get("10");
        System.out.println("xxxxxxx"+variant);
    }

    @Test
    public void testAllKeys(){
        Set<String> keys = redisTemplate.keys("ItemValue:*");
        System.out.println(keys);
    }

}