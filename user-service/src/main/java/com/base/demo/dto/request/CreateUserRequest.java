package com.base.demo.dto.request;

import com.base.demo.enums.Role;
import com.base.demo.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @Schema(description = "Tên đăng nhập của người dùng", example = "john_doe")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Schema(description = "Mật khẩu của người dùng", example = "P@ssw0rd!")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Schema(description = "Họ và tên đầy đủ của người dùng", example = "John Doe")
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @Schema(description = "Địa chỉ email của người dùng", example = "lamthon@gmail.com")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Schema(description = "Vai trò của người dùng", example = "ADMIN")
    @NotBlank(message = "Vai trò không được để trống")
    private String role;

    @Schema(description = "Trạng thái của người dùng", example = "ACTIVE")
    @NotBlank(message = "Trạng thái không được để trống")
    private String status;
}
