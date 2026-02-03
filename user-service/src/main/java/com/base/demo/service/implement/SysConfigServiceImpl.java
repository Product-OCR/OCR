package com.base.demo.service.implement;

import com.base.demo.enums.ErrorCode;
import com.base.demo.exception.BaseException;
import com.base.demo.model.SystemConfigEntity;
import com.base.demo.repository.SysConfigRepository;
import com.base.demo.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;

    @Override
    public String getValue(String key) {
        return sysConfigRepository.findByConfigKeyAndIsActiveTrue(key)
                .map(SystemConfigEntity::getConfigValue)
                .orElseThrow(() -> new BaseException(ErrorCode.CONFIG_KEY_INVALID));
    }
}
