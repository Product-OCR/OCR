package com.ocr.utils;

public class ProjectUtil {
    public String generateProjectCode(Long projectId) {
        return "BM-" + String.format("%05d", projectId);
    }

}
