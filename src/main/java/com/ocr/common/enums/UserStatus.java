package com.ocr.common.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("Đang hoạt động"),
    INACTIVE("Vô hiệu hóa");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }
}
