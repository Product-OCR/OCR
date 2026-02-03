package com.base.demo.dto.request;

import com.base.demo.enums.Role;
import com.base.demo.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterRequest {

    @Schema(description = "Keyword to search in username, full name, or email", example = "john")
    private String keyword;

    @Schema(description = "Role of the user", example = "ADMIN")
    private Role role;

    @Schema(description = "Status of the user", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "Start date for filtering users", example = "2024-01-01T00:00:00")
    private LocalDateTime fromDate;

    @Schema(description = "End date for filtering users", example = "2024-12-31T23:59:59")
    private LocalDateTime toDate;
}
