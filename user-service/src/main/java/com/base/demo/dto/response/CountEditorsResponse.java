package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountEditorsResponse {
    @Schema(description = "Total number of editors", example = "42")
    private Long editorsCount;
}

