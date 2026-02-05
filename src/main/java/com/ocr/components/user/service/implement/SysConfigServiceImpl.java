package com.ocr.components.user.service.implement;

import com.ocr.common.enums.ErrorCode;
import com.ocr.common.exception.BaseException;
import com.ocr.components.user.model.SystemConfigEntity;
import com.ocr.components.user.repository.SysConfigRepository;
import com.ocr.components.user.service.SysConfigService;
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
