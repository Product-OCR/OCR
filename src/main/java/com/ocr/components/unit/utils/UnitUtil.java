package com.ocr.components.unit.utils;

public class UnitUtil {

    public String generateUnitCode(Long unitId) {
        return "BM-" + String.format("%05d", unitId);
    }
}
