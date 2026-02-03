package com.base.demo.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Quản trị viên"),
    PROJECT_MANAGER("Quản lí dự án"),
    EDITOR("Cán bộ số hóa");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
