package com.xcs.server.web.controllor;

import com.xcs.server.web.domain.Setting;
import com.xcs.server.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SettingController {

    @Autowired
    SettingService settingService;

    @RequestMapping("/settings/get")
    public ResponseMsg get() {
        try {
            List<Setting> all = settingService.getAll();
            Map<String, String> map = new HashMap<>();
            all.forEach(setting -> map.put(setting.getName(), setting.getValue()));
            return ResponseMsg.getSuccess(map);
        } catch (Exception e) {
            return ResponseMsg.getFailed("获取设置信息失败");
        }
    }

    @RequestMapping("/settings/save")
    public ResponseMsg save(@RequestParam Map<String, String> settings) {
        try {
            List<Setting> list = new ArrayList<>();
            settings.forEach((key, val) -> list.add(new Setting(key, val)));
            settingService.save(list);
            return ResponseMsg.getSuccess("保存设置信息成功");
        } catch (Exception e) {
            return ResponseMsg.getFailed("保存设置信息失败");
        }
    }
}
