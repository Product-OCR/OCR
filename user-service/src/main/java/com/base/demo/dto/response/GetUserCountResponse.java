package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserCountResponse {

    @Schema(description = "Total number of users", example = "150")
    private Long userCount;
}
