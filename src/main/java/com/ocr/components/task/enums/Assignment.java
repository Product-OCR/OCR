package com.ocr.components.task.enums;

import lombok.Getter;

@Getter
public enum Assignment {

    SEQUENTIAL("Tuần tự"),
    BALANCED("Cân bằng");

    private final String assignment;

    Assignment(String assignment) {
        this.assignment = assignment;
    }
}
