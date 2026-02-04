package com.ocr.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProjectRequest {
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String approachMethod;
    private Long projectManagerId;
    private Long unitId;
    private List<Long> formIds;
    private String note;
    private String status;
}
