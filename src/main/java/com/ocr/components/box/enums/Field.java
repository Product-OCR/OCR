package com.ocr.components.box.enums;

import lombok.Getter;

@Getter
public enum Field {

    NAME("Tên hồ sơ");

    private final String name;

    Field(String name) {
        this.name = name;
    }
}
