package com.ocr.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectSearchRequest {
    private String keyword; // Tìm theo tên hoặc mã dự án
    private Long unitId; // Lọc theo đơn vị
    private String status; // Lọc theo trạng thái
    private String approach; // Lọc theo phương pháp
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private int page = 0; // Trang hiện tại
    private int size = 20; // PageSize mặc định là 20 theo yêu cầu của cậu
}
