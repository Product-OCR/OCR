package com.ocr.common.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProjectsByUnitIdResponse {

    private Long projectId;
    private Long unitId;
    private String projectName;
    private String projectStatus;
    private String approachMethod;
}
