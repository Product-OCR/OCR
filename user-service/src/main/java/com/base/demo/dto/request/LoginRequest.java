package com.base.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "Tên đăng nhập của người dùng", example = "john_doe")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Schema(description = "Mật khẩu của người dùng", example = "P@ssw0rd!")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
