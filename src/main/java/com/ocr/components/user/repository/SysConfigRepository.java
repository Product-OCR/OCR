package com.ocr.components.user.repository;

import com.ocr.components.user.model.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SysConfigRepository extends JpaRepository<SystemConfigEntity, Long> {

    Optional<SystemConfigEntity> findByConfigKey(String configKey);
    Optional<SystemConfigEntity> findByConfigKeyAndIsActiveTrue(String configKey);
}
