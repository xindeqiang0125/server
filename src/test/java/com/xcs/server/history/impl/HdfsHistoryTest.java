package com.xcs.server.history.impl;

import com.xcs.server.opc.data.Value;
import com.xcs.server.opc.memory.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HdfsHistoryTest {

    @Autowired
    MySqlHistory mySqlHistory;

    @Autowired
    HdfsHistory hdfsHistory;

    ValueMap map = new ValueMap();

    @Test
    public void saveHistoryDatasMysql(){
        mySqlHistory.saveHistoryDatas(map, LocalDateTime.now());
    }

    @Test
    public void getHistoryDatasMysql(){
        List dataHistory = mySqlHistory.getDataHistory(1, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        System.out.println(dataHistory);
        dataHistory = mySqlHistory.getDataHistory(2, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        System.out.println(dataHistory);
        dataHistory = mySqlHistory.getDataHistory(3, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        System.out.println(dataHistory);
        dataHistory = mySqlHistory.getDataHistory(4, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        System.out.println(dataHistory);
    }

    @Test
    public void saveHistoryDatasHdsf() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(3000);
            hdfsHistory.saveHistoryDatas(map, LocalDateTime.now());
        }
    }

    @Before
    public void setUp(){
        map.put("1",new Value("15",Value.Type.INTEGER));
        map.put("1",new Value("15",Value.Type.INTEGER));
        map.put("1",new Value("15",Value.Type.INTEGER));
        map.put("1",new Value("15",Value.Type.INTEGER));
        map.put("1",new Value("15",Value.Type.INTEGER));

        map.put("2",new Value("15.54565",Value.Type.DOUBLE));
        map.put("2",new Value("15.54565",Value.Type.DOUBLE));
        map.put("2",new Value("15.54565",Value.Type.DOUBLE));
        map.put("2",new Value("15.54565",Value.Type.DOUBLE));
        map.put("2",new Value("15.54565",Value.Type.DOUBLE));

        map.put("3",new Value("suvhsodvh",Value.Type.STRING));
        map.put("3",new Value("suvhsodvh",Value.Type.STRING));
        map.put("3",new Value("suvhsodvh",Value.Type.STRING));
        map.put("3",new Value("suvhsodvh",Value.Type.STRING));
        map.put("3",new Value("suvhsodvh",Value.Type.STRING));

        map.put("4",new Value("false",Value.Type.BOOLEAN));
        map.put("4",new Value("false",Value.Type.BOOLEAN));
        map.put("4",new Value("false",Value.Type.BOOLEAN));
        map.put("4",new Value("false",Value.Type.BOOLEAN));
        map.put("4",new Value("false",Value.Type.BOOLEAN));
    }
}