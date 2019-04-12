package com.xcs.server.web.repository;

import com.xcs.server.web.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting,String> {
}
