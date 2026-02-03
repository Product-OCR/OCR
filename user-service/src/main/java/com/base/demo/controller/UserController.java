package com.base.demo.controller;

import com.base.demo.dto.request.CreateUserRequest;
import com.base.demo.dto.request.UpdateUserRequest;
import com.base.demo.dto.request.UserFilterRequest;
import com.base.demo.dto.response.*;
import com.base.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@EnableMethodSecurity
@Tag(name = "User Management", description = "Quản lý người dùng dưới quyền Admin")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Tạo mới user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. Case Thành công (200) - Lấy data mẫu từ DTO GetUserResponse
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Tạo thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"id\": 1, \"username\": \"admin_vjp\", \"fullName\": \"Nguyen Van A\", \"email\": \"admin@gmail.com\", \"role\": \"ADMIN\", \"status\": \"ACTIVE\" } }"
                            )
                    )
            ),

            // 2. Case Dữ liệu không hợp lệ (400) - Trùng khớp với ErrorCode.ARGUMENT_NOT_VALID
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Tham số đầu vào không hợp lệ",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Argument",
                                    value = "{ \"code\": 1006, \"message\": \"Tham số đầu vào không hợp lệ\", \"result\": null }"
                            )
                    )
            ),

            // 3. Case Tên đăng nhập đã tồn tại (409) - Trùng khớp với ErrorCode.USERNAME_ALREADY_EXISTS
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Xung đột: Tên đăng nhập đã tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Exists",
                                    value = "{ \"code\": 1005, \"message\": \"Tên đăng nhập đã tồn tại\", \"result\": null }"
                            )
                    )
            )
    })
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @Operation(summary = "Cập nhật thông tin user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Cập nhật thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"id\": 10, \"username\": \"admin_update\", \"fullName\": \"Nguyen Van A\", \"email\": \"update@gmail.com\", \"role\": \"ADMIN\", \"status\": \"ACTIVE\" } }"
                            )
                    )
            ),
            // 2. VALIDATION ERROR (400)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Dữ liệu đầu vào không hợp lệ / Lỗi Logic (Trùng tên, Không tìm thấy...)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Lỗi Validate Form",
                                            summary = "Lỗi do @Valid (Email sai, thiếu trường...)",
                                            value = "{ \"code\": 1006, \"message\": \"Tham số đầu vào không hợp lệ\", \"result\": null }"
                                    ),
                                    @ExampleObject(
                                            name = "Lỗi Logic (Chung)",
                                            summary = "Lỗi do BaseException (Trùng tên, ID sai...)",
                                            value = "{ \"code\": 1005, \"message\": \"Tên đăng nhập đã tồn tại\", \"result\": null }"
                                    )
                            }
                    )
            ),
            // 3. FORBIDDEN (403) - Spring Security tự trả về
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Forbidden",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            ),
            // 4. NOT FOUND (404) - Chỉ để Doc cho đẹp (Thực tế code cậu đang trả về 400)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Người dùng không tồn tại (Doc)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Not Found",
                                    value = "{ \"code\": 1001, \"message\": \"Người dùng không tồn tại\", \"result\": null }"
                            )
                    )
            )
    })
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {

        return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
    }

    @Operation(summary = "Lấy chi tiết user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lấy thông tin thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"id\": 1, \"username\": \"admin_vjp\", \"accountCode\": \"ACC12345\", \"password\": \"$2a$10$...\", \"fullName\": \"Nguyen Van A\", \"email\": \"admin@gmail.com\", \"role\": \"ADMIN\", \"status\": \"ACTIVE\", \"createdAt\": \"2024-01-01T12:00:00\", \"updatedAt\": \"2024-06-01T15:30:00\" } }"
                            )
                    )
            ),
            // 2. BAD REQUEST (400) - Lỗi định dạng ID
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "ID không hợp lệ (Không phải số)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid ID Format",
                                    value = "{ \"code\": 1006, \"message\": \"Tham số đầu vào không hợp lệ (ID phải là số)\", \"result\": null }"
                            )
                    )
            ),
            // 3. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 4. NOT FOUND (404) - Doc hiển thị đẹp (Dù code thực tế trả về 400 do ErrorCode)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Người dùng không tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Not Found",
                                    value = "{ \"code\": 1001, \"message\": \"Người dùng không tồn tại\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GetUserResponse>> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Vô hiệu hóa tài khoản (Soft Delete)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200) - Khớp với DisableUserResponse
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Vô hiệu hóa thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    // Chú ý: status là INACTIVE (theo logic service), result khớp các field của DTO
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"id\": 123, \"username\": \"john_doe\", \"fullName\": \"John Doe\", \"email\": \"lamthon@mail.com\", \"role\": \"EDITOR\", \"status\": \"INACTIVE\" } }"
                            )
                    )
            ),
            // 2. BAD REQUEST (400) - Lỗi định dạng ID
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "ID không hợp lệ (Không phải số)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid ID",
                                    value = "{ \"code\": 1006, \"message\": \"Tham số đầu vào không hợp lệ\", \"result\": null }"
                            )
                    )
            ),
            // 3. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 4. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            ),
            // 5. NOT FOUND (404) - Khớp với lỗi USER_NOT_EXISTED (1001)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Người dùng không tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Not Found",
                                    value = "{ \"code\": 1001, \"message\": \"Người dùng không tồn tại\", \"result\": null }"
                            )
                    )
            )
    })
    @PatchMapping("/{userId}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DisableUserResponse>> disableUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.disableUser(userId), HttpStatus.OK);
    }

    @Operation(summary = "Đếm số lượng Quản lí dự án(ADMIN)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Trả về số lượng quản lí dự án",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    // Khớp với field 'projectManagersCount' trong DTO
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"projectManagersCount\": 15 } }"
                            )
                    )
            ),
            // 2. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 3. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/count-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountProjectManagersResponse>> countProjectManagers() {
        return new ResponseEntity<>(userService.countProjectManagers(), HttpStatus.OK);
    }

    @Operation(summary = "Đếm số lượng Biên tập viên (EDITOR)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Trả về số lượng biên tập viên",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    // Giả định DTO CountEditorsResponse có field 'editorsCount'
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"editorsCount\": 20 } }"
                            )
                    )
            ),
            // 2. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 3. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/count-editors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountEditorsResponse>> countEditors() {
        return new ResponseEntity<>(userService.countEditors(), HttpStatus.OK);
    }


    @Operation(summary = "Đếm số lượng Kiểm duyệt viên (PROJECT_MANAGER)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Trả về số lượng kiểm duyệt viên",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    // Khớp với field 'moderatorCount' trong DTO CountModeratorsResponse
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"moderatorCount\": 15 } }"
                            )
                    )
            ),
            // 2. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 3. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/count-moderators")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountModeratorsResponse>> countModerators() {
        return new ResponseEntity<>(userService.countModerators(), HttpStatus.OK);
    }

    @Operation(summary = "Đếm danh sách tất cả người dùng")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Trả về số lượng người dùng",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Thành công",
                                    // Khớp với field 'userCount' trong DTO GetUserCountResponse
                                    value = "{ \"code\": 1000, \"message\": \"Thành công\", \"result\": { \"userCount\": 150 } }"
                            )
                    )
            ),
            // 2. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 3. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/user-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GetUserCountResponse>> getUserCount() {
        return new ResponseEntity<>(userService.getUserCount(), HttpStatus.OK);
    }

    @Operation(summary = "Lấy danh sách người dùng theo bộ lọc (Phân trang)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            // 1. SUCCESS (200) - Trả về Page<GetUserResponse>
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lấy danh sách thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Page Result",
                                    // JSON mô phỏng cấu trúc chuẩn của Spring Data Page
                                    value = "{\n" +
                                            "  \"code\": 1000,\n" +
                                            "  \"message\": \"Thành công\",\n" +
                                            "  \"result\": {\n" +
                                            "    \"content\": [\n" +
                                            "      {\n" +
                                            "        \"id\": 1,\n" +
                                            "        \"username\": \"admin_vjp\",\n" +
                                            "        \"accountCode\": \"ACC001\",\n" +
                                            "        \"fullName\": \"Nguyen Van A\",\n" +
                                            "        \"email\": \"admin@gmail.com\",\n" +
                                            "        \"role\": \"ADMIN\",\n" +
                                            "        \"status\": \"ACTIVE\",\n" +
                                            "        \"createdAt\": \"2024-01-27T10:00:00\",\n" +
                                            "        \"updatedAt\": \"2024-01-27T10:00:00\"\n" +
                                            "      },\n" +
                                            "      {\n" +
                                            "        \"id\": 2,\n" +
                                            "        \"username\": \"editor_01\",\n" +
                                            "        \"accountCode\": \"ACC002\",\n" +
                                            "        \"fullName\": \"Tran Van B\",\n" +
                                            "        \"email\": \"editor@gmail.com\",\n" +
                                            "        \"role\": \"EDITOR\",\n" +
                                            "        \"status\": \"INACTIVE\",\n" +
                                            "        \"createdAt\": \"2024-01-26T09:30:00\",\n" +
                                            "        \"updatedAt\": \"2024-01-26T09:30:00\"\n" +
                                            "      }\n" +
                                            "    ],\n" +
                                            "    \"pageable\": {\n" +
                                            "      \"pageNumber\": 0,\n" +
                                            "      \"pageSize\": 20,\n" +
                                            "      \"sort\": {\n" +
                                            "        \"empty\": false,\n" +
                                            "        \"sorted\": true,\n" +
                                            "        \"unsorted\": false\n" +
                                            "      },\n" +
                                            "      \"offset\": 0,\n" +
                                            "      \"paged\": true,\n" +
                                            "      \"unpaged\": false\n" +
                                            "    },\n" +
                                            "    \"last\": true,\n" +
                                            "    \"totalPages\": 1,\n" +
                                            "    \"totalElements\": 2,\n" +
                                            "    \"size\": 20,\n" +
                                            "    \"number\": 0,\n" +
                                            "    \"sort\": {\n" +
                                            "      \"empty\": false,\n" +
                                            "      \"sorted\": true,\n" +
                                            "      \"unsorted\": false\n" +
                                            "    },\n" +
                                            "    \"first\": true,\n" +
                                            "    \"numberOfElements\": 2,\n" +
                                            "    \"empty\": false\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            ),
            // 2. BAD REQUEST (400) - Thường gặp khi sai định dạng ngày tháng
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Lỗi định dạng bộ lọc (Ngày tháng, Enum...)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Date Format Error",
                                    value = "{ \"code\": 1006, \"message\": \"Tham số đầu vào không hợp lệ (Ngày tháng sai định dạng ISO-8601)\", \"result\": null }"
                            )
                    )
            ),
            // 3. UNAUTHORIZED (401)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa đăng nhập / Token hết hạn",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized",
                                    value = "{ \"code\": 9999, \"message\": \"Bạn chưa đăng nhập\", \"result\": null }"
                            )
                    )
            ),
            // 4. FORBIDDEN (403)
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Không có quyền truy cập",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Access Denied",
                                    value = "{ \"code\": 9999, \"message\": \"Access Denied\", \"result\": null }"
                            )
                    )
            )
    })
    @GetMapping("/user-filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<GetUserResponse>>> getUsersByFilter(@ParameterObject UserFilterRequest filterRequest
            , @Parameter(description = "Số trang bắt đầu từ 0") @RequestParam(defaultValue = "0") Integer page
            , @Parameter(description = "Kích thước trang") @RequestParam(defaultValue = "20") Integer size){


            return new ResponseEntity<>(userService.getUsersByFilter(filterRequest, page, size), HttpStatus.OK);
        }
}
