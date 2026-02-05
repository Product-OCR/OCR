package com.ocr.common.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFormResponse {

    private Long id;
    private String filed;
}
