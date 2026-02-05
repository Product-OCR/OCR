package com.ocr.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenCodeUtil {
    public String generateFastCode(String front) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        int randomPart = new Random().nextInt(9000) + 1000;
        return front + timestamp + randomPart;
    }
}
