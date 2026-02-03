package com.ocr.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountProjectsByUnitIdResponse {
    private Long count;
}

