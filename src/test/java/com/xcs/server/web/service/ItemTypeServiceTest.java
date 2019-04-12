package com.xcs.server.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemTypeServiceTest {
    @Autowired
    ItemTypeService itemTypeService;

    @Test
    public void getItemType() {
        String itemType = itemTypeService.getItemType(14);
        System.out.println(itemType);
    }
}