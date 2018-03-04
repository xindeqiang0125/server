package com.xcs.server.controllor;

import com.xcs.server.domain.Settings;
import com.xcs.server.opc.ServerSettings;
import com.xcs.server.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsControllor {
    @Autowired
    private SettingsService settingsService;

    @RequestMapping("/settings/get")
    public Settings getSettings(){
        return ServerSettings.getSettings();
    }

    @RequestMapping("/settings/save")
    public ResponseMsg saveSettings(Settings settings){
//        System.out.println(settings.getSaveInterval());
        try {
            ServerSettings.setSettings(settings);
            settingsService.saveSettings(settings);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseMsg.getFailed("保存设置失败!!!");
        }
        return ResponseMsg.getSuccess("保存设置成功!!!");
    }
}
