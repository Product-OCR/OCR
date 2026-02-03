package com.base.demo.repository;

import com.base.demo.model.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SysConfigRepository extends JpaRepository<SystemConfigEntity, Long> {
    Optional<SystemConfigEntity> findByConfigKeyAndIsActiveTrue(String configKey);
}
