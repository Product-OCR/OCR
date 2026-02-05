package com.ocr.components.user.controller;

import com.ocr.common.dto.response.ApiResponse;
import com.ocr.components.user.dto.request.CreateUserRequest;
import com.ocr.components.user.dto.request.UpdateUserRequest;
import com.ocr.components.user.dto.request.UserFilterRequest;
import com.ocr.components.user.dto.response.*;
import com.ocr.components.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Quản lý người dùng")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Tạo mới user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @Operation(summary = "Cập nhật user")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
    }

    @Operation(summary = "Lấy chi tiết user")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GetUserResponse>> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Vô hiệu hóa tài khoản")
    @PatchMapping("/{userId}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DisableUserResponse>> disableUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.disableUser(userId), HttpStatus.OK);
    }

    @Operation(summary = "Đếm số lượng Admin")
    @GetMapping("/count-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountProjectManagersResponse>> countProjectManagers() {
        return new ResponseEntity<>(userService.countProjectManagers(), HttpStatus.OK);
    }

    @Operation(summary = "Đếm số lượng Biên tập viên")
    @GetMapping("/count-editors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountEditorsResponse>> countEditors() {
        return new ResponseEntity<>(userService.countEditors(), HttpStatus.OK);
    }

    @Operation(summary = "Đếm số lượng Kiểm duyệt viên")
    @GetMapping("/count-moderators")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountModeratorsResponse>> countModerators() {
        return new ResponseEntity<>(userService.countModerators(), HttpStatus.OK);
    }

    @Operation(summary = "Đếm tổng số người dùng")
    @GetMapping("/user-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GetUserCountResponse>> getUserCount() {
        return new ResponseEntity<>(userService.getUserCount(), HttpStatus.OK);
    }

    @Operation(summary = "Lấy danh sách người dùng theo bộ lọc")
    @GetMapping("/user-filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<GetUserResponse>>> getUsersByFilter(
            @ParameterObject UserFilterRequest filterRequest,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return new ResponseEntity<>(userService.getUsersByFilter(filterRequest, page, size), HttpStatus.OK);
    }
}
