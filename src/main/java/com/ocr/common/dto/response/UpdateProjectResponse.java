package com.ocr.common.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProjectResponse {

    private Long projectId;
    private Long unitId;
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String approachMethod;
    private Long projectManagerId;
    private List<Long> formId;
    private String note;
}
