package com.base.demo.dto.response;

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

    @Schema(description = "Access token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh token for obtaining new access tokens", example = "dGhpcy1pcz1hLXJlZnJlc2gtdG9rZW4uLi4=")
    private String refreshToken;

    @Schema(description = "ID of the user associated with the token", example = "123")
    private Long userId;

    @Schema(description = "Username of the user associated with the token", example = "john_doe")
    private String username;

    @Schema(description = "Role of the user", example = "ADMIN")
    private String role;
}
