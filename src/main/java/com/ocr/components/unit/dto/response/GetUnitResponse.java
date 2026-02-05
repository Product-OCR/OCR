package com.ocr.components.unit.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUnitResponse {

    private Long id;
    private String unitName;
    private String unitCode;
    private Integer totalProjects;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String contactPerson;
    private String contactNumber;
    private String address;
}
