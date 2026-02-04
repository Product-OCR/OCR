package com.ocr.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilterResponse {
    private Long projectId;
    private String projectCode;
    private String projectName;
    private String projectStatus;
    private String approachMethod;
    private String managerName;
    private String templateName;
    private String actualFinishDate;
    private String timeRange;
    private Float ocrProgress;
    private Float verificationProgress;
    private String unitName;
}
