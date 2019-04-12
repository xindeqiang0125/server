package com.xcs.server.setting;

import java.util.List;

public interface SettingService {
    void save(List<Setting> settings);
    List<Setting> getAll();
    Setting getOne(String key);
}
