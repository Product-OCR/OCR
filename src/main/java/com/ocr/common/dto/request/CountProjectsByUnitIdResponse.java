package com.ocr.common.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountProjectsByUnitIdResponse {
    private Long count;
}
