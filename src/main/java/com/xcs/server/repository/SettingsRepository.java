package com.xcs.server.repository;

import com.xcs.server.domain.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings,Integer> {
}
