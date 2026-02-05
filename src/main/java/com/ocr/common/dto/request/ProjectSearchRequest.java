package com.ocr.common.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectSearchRequest {
    private String keyword;
    private Long unitId;
    private String status;
    private String approach;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int page = 0;
    private int size = 20;
}
