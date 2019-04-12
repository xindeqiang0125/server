package com.xcs.server.web.service;

import com.xcs.server.web.domain.Setting;

import java.util.List;

public interface SettingService {
    void save(List<Setting> settings);
    List<Setting> getAll();
    Setting getOne(String key);
}
