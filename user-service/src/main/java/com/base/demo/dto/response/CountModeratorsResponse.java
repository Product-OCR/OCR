package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountModeratorsResponse {

    @Schema(description = "Total number of moderators", example = "15")
    private Long moderatorCount;
}
