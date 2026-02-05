package com.ocr.components.unit.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUnitRequest {

    @NotBlank(message = "Không được để trống tên đơn vị")
    private String unitName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String address;
    private String contactPerson;
    private String contactNumber;
}
