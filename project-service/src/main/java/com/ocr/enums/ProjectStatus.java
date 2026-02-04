package com.ocr.enums;

public enum ProjectStatus {

    COMPLETED("Hoàn thành"),
    IN_PROGRESS("Đang thi công"),
    OPEN("Mở");

    private final String label;

    ProjectStatus(String label) {
        this.label = label;
    }
}
