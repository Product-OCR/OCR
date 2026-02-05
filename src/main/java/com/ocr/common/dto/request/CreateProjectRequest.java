package com.ocr.common.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProjectRequest {

    private Long unitId;
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String approachMethod;
    private Long projectManagerId;
    private List<Long> formId;
    private String note;
}
