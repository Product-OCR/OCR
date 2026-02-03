package com.base.demo.dto.response;

import com.base.demo.enums.Role;
import com.base.demo.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Account code of the user", example = "ACC12345")
    private String username;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Email address of the user", example = "lamthon@gmail.com")
    private String email;

    @Schema(description = "Role of the user", example = "ADMIN")
    private String role;

    @Schema(description = "Status of the user", example = "ACTIVE")
    private String status;
}
