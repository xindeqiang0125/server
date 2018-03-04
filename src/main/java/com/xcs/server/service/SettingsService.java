package com.xcs.server.service;

import com.xcs.server.domain.Settings;

public interface SettingsService {
    Settings getSettings();

    void saveSettings(Settings settings);
}
