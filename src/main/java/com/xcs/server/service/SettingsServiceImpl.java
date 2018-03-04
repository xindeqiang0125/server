package com.xcs.server.service;

import com.xcs.server.domain.Settings;
import com.xcs.server.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingsServiceImpl implements SettingsService {
    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public Settings getSettings() {
        return settingsRepository.findOne(1);
    }

    @Override
    public void saveSettings(Settings settings) {
        settings.setId(1);
        settingsRepository.save(settings);
    }
}
