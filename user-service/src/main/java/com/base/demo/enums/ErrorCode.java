package com.base.demo.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS(1000, "Thành công", HttpStatus.OK),
    UNAUTHENTICATED(9999, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXISTED(1001, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1002, "Thông tin đăng nhập không hợp lệ", HttpStatus.BAD_REQUEST),
    CONFIG_NOT_FOUND(1003, "Cấu hình không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    CONFIG_KEY_INVALID(1004, "Khóa cấu hình không hợp lệ", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(1005, "Tên đăng nhập đã tồn tại", HttpStatus.BAD_REQUEST),
    ARGUMENT_NOT_VALID(1006, "Tham số đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(5000, "Lỗi máy chủ nội bộ", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1007, "Bạn không có quyền truy cập tài nguyên này", HttpStatus.FORBIDDEN);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
