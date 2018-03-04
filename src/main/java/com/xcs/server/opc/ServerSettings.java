package com.xcs.server.opc;

import com.xcs.server.domain.Settings;
import com.xcs.server.service.SettingsService;
import com.xcs.server.service.SettingsServiceImpl;
import com.xcs.server.util.SpringUtil;

public class ServerSettings {
    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) {
            SettingsService settingsService = SpringUtil.getApplicationContext().getBean(SettingsServiceImpl.class);
            try {
                settings=settingsService.getSettings();
            } catch (Exception e) {
                //e.printStackTrace();
                settings=new Settings(1000,5000);
            }
        }
        return settings;
    }

    public static void setSettings(Settings settings) {
        ServerSettings.settings = settings;
    }
}
