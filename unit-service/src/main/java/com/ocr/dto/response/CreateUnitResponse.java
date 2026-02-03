package com.ocr.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUnitResponse {

    private Long id;
    private String unitCode;
    private String unitName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String address;
    private String contactPerson;
    private String contactNumber;
}
