package com.ocr.common.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUnitsResponse {

    private Long unitId;
    private String unitName;
    private String unitCode;
    private Long totalProject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
