package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountProjectManagersResponse {

    @Schema(description = "Total number of project managers", example = "15")
    private Long projectManagersCount;
}
