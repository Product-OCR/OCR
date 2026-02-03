package com.base.demo.service.implement;

import com.base.demo.enums.ErrorCode;
import com.base.demo.exception.ConfigNotFoundException;
import com.base.demo.model.SystemConfigEntity;
import com.base.demo.repository.SystemConfigRepository;
import com.base.demo.service.ConfigService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final SystemConfigRepository systemConfigRepository;

    @Override
    @Cacheable(value = "configCache", key = "#key")
    public String getConfigValue(String key) {

        return systemConfigRepository.findByConfigKey(key)
                .map(SystemConfigEntity::getConfigValue)
                .orElseThrow(() -> new ConfigNotFoundException(ErrorCode.CONFIG_NOT_FOUND.name()));
    }
}

