package com.base.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
public class DisableUserResponse {

    @Schema(description = "ID của người dùng đã bị vô hiệu hóa", example = "123")
    private Long id;

    @Schema(description = "Tên đăng nhập của người dùng đã bị vô hiệu hóa", example = "john_doe")
    private String username;

    @Schema(description = "Họ và tên đầy đủ của người dùng đã bị vô hiệu hóa", example = "John Doe")
    private String fullName;

    @Schema(description = "Địa chỉ email của người dùng đã bị vô hiệu hóa", example = "lanthon@mail.com")
    private String email;

    @Schema(description = "Vai trò của người dùng đã bị vô hiệu hóa", example = "ADMIN")
    private String role;

    @Schema(description = "Trạng thái của người dùng đã bị vô hiệu hóa", example = "DISABLED")
    private String status;
}
