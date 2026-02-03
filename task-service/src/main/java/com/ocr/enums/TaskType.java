package com.ocr.enums;

public enum TaskType {

    DATA_EXTRACTION("Bóc tách dữ liệu"),
    DATA_VERIFICATION("Kiểm tra dữ liệu");

    private final String taskType;

    TaskType(String taskType) {

        this.taskType = taskType;
    }

}
