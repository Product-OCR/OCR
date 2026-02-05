package com.ocr.common.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Quản trị viên"),
    PROJECT_MANAGER("Kiểm duyệt viên"),
    EDITOR("Biên tập viên");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
