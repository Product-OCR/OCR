package com.ocr.components.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @Schema(description = "Tên đăng nhập", example = "john_doe")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Schema(description = "Mật khẩu", example = "P@ssw0rd!")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Schema(description = "Họ và tên", example = "John Doe")
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @Schema(description = "Email", example = "lamthon@gmail.com")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Schema(description = "Vai trò", example = "ADMIN")
    private String role;

    @Schema(description = "Trạng thái", example = "ACTIVE")
    private String status;
}
