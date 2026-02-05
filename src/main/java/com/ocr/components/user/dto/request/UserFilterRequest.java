package com.ocr.components.user.dto.request;

import com.ocr.common.enums.Role;
import com.ocr.common.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterRequest {

    @Schema(description = "Keyword search", example = "john")
    private String keyword;

    @Schema(description = "Role", example = "ADMIN")
    private Role role;

    @Schema(description = "Status", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "From date", example = "2024-01-01T00:00:00")
    private LocalDateTime fromDate;

    @Schema(description = "To date", example = "2024-12-31T23:59:59")
    private LocalDateTime toDate;
}
