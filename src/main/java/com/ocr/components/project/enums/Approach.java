package com.ocr.components.project.enums;

import lombok.Getter;

@Getter
public enum Approach {
    BASIC("Basic"),
    SEGMENTATION("Segmentation"),
    TABLE("Table");

    private final String methodName;

    Approach(String methodName) {
        this.methodName = methodName;
    }
}
