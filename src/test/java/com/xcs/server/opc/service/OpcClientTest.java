package com.xcs.server.opc.service;

import com.xcs.server.web.controllor.LoginControllor;
import com.xcs.server.opc.OpcClient;
import com.xcs.server.opc.memory.DataMemory;
import com.xcs.server.util.SpringUtil;
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
    DataMemory dataMemory;

    @Test
    public void opcClient(){
        OpcClient opcClient = new OpcClient();
        opcClient.start();
    }

    @Test
    public void start() throws InterruptedException {
        OpcClient client = new OpcClient();
        client.start();
        Thread.sleep(5000);
        client.stop();
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

    @Test
    public void testGetBean(){
        System.out.println(SpringUtil.getApplicationContext().getBean(LoginControllor.class));
    }
}