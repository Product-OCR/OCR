package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @Schema(description = "Account code of the user", example = "ACCT12345")
    private String accountCode;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Email address of the user", example = "lamthon@gmail.com")
    private String email;

    @Schema(description = "Role of the user", example = "ADMIN")
    private String role;

    @Schema(description = "Status of the user", example = "ACTIVE")
    private String status;

    @Schema(description = "Timestamp when the user was created", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated", example = "2024-06-01T15:30:00")
    private LocalDateTime updatedAt;
}
