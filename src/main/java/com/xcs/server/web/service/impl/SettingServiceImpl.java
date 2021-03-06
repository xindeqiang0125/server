package com.xcs.server.web.service.impl;

import com.xcs.server.web.domain.Setting;
import com.xcs.server.web.repository.SettingRepository;
import com.xcs.server.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingRepository settingRepository;

    @Override
    public void save(List<Setting> settings) {
        settingRepository.save(settings);
    }

    @Override
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    @Override
    public Setting getOne(String key) {
        return settingRepository.findOne(key);
    }
}
