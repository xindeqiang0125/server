package com.xcs.server.setting;

import com.xcs.server.web.domain.Setting;
import com.xcs.server.web.service.SettingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SettingServiceTest {
    @Autowired
    SettingService settingService;

    @Test
    public void save() {
        List<Setting> settings = Arrays.asList(new Setting("school", "trdyu"), new Setting("age", "22"));
        settingService.save(settings);
    }

    @Test
    public void getAll() {
        List<Setting> all = settingService.getAll();
        all.forEach(setting -> System.out.println(setting));
    }

    @Test
    public void getOne() {
        Setting school = settingService.getOne("school");
        System.out.println(school);
    }
}