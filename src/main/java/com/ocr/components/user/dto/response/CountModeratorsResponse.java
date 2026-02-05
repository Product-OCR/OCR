package com.ocr.components.user.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountModeratorsResponse {
    private Long moderatorCount;
}
