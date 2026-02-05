package com.ocr.components.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    @Schema(description = "Access token")
    private String accessToken;

    private String refreshToken;
    private Long userId;
    private String username;
    private String role;
}
