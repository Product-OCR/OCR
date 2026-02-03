//package com.base.demo.controller;
//
//
//import com.base.demo.dto.request.CreateUserRequest;
//import com.base.demo.dto.request.UpdateUserRequest;
//import com.base.demo.dto.request.UserFilterRequest;
//import com.base.demo.dto.response.*;
//
//import com.base.demo.enums.ErrorCode;
//import com.base.demo.enums.Role;
//import com.base.demo.enums.UserStatus;
//import com.base.demo.exception.BaseException;
//import com.base.demo.model.Users;
//import com.base.demo.repository.UsersRepository;
//import com.base.demo.security.jwt.JwtUtils;
//import com.base.demo.security.service.CustomUserDetailsService;
//import com.base.demo.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class) // Chỉ load Controller này, không load cả App
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc; // Công cụ giả lập request HTTP
//
//    @MockBean
//    private UserService userService; // Mock Service để Controller gọi
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    @MockBean
//    private CustomUserDetailsService customUserDetailsService; // <--- THÊM MỚI: Mock cho JwtAuthenticationFilter (Param 1)
//
//    @Autowired
//    private ObjectMapper objectMapper; // Dùng để convert Object -> JSON String
//
//    @MockBean
//    private JwtUtils jwtUtils; //
//
//    private CreateUserRequest createUserRequest;
//    private CreateUserResponse createUserResponse;
//    private ApiResponse<CreateUserResponse> createUserResponseApiResponse;
//
//    private UpdateUserRequest updateUserRequest;
//    private UpdateUserResponse updateUserResponse;
//    private ApiResponse<UpdateUserResponse> updateUserResponseApiResponse;
//
//    @BeforeEach
//    void setUpUpdateUser() {
//        // 1. Chuẩn bị dữ liệu đầu vào (Request)
//        updateUserRequest = UpdateUserRequest.builder()
//                .fullName("Admin Updated")
//                .username("testadmin")
//                .password("123456")
//                .email("lamthon@gmail.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//
//        // 2. Chuẩn bị dữ liệu đầu ra mong đợi từ Service (Response)
//        updateUserResponse = UpdateUserResponse.builder()
//                .id(1L)
//                .username("testadmin")
//                .fullName("Admin Updated")
//                .email("lamthon@gmail.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//    }
//
//    @BeforeEach
//    void setUpCreateUser() {
//        // 1. Chuẩn bị dữ liệu đầu vào (Request)
//        createUserRequest = CreateUserRequest.builder()
//                .username("testadmin")
//                .password("password123")
//                .fullName("Admin Test")
//                .email("admin@test.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//
//        // 2. Chuẩn bị dữ liệu đầu ra mong đợi từ Service (Response)
//        createUserResponse = CreateUserResponse.builder()
//                .id(1L)
//                .username("testadmin")
//                .fullName("Admin Test")
//                .email("admin@test.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//
//        // 3. Bọc trong ApiResponse chuẩn
//        createUserResponseApiResponse = ApiResponse.<CreateUserResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(createUserResponse)
//                .build();
//    }
//
//    // --- CASE 1: Tạo User thành công (Happy Path) ---
//    @Test
//    @DisplayName("Create User: Trả về 200 OK và JSON đúng chuẩn khi createUserRequest hợp lệ")
//    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Giả lập User có quyền ADMIN
//    void createUser_ValidRequest_Success() throws Exception {
//        // Given: Khi gọi service thì trả về kết quả thành công
//        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(createUserResponseApiResponse);
//
//        // When & Then: Gọi API và kiểm tra
//        mockMvc.perform(post("/api/admin/users")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createUserRequest))) // Convert object request thành JSON string
//
//                // Kiểm tra HTTP Status
//                .andExpect(status().isOk())
//
//                // Kiểm tra nội dung JSON trả về (Dùng JsonPath)
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.username").value("testadmin"))
//                .andExpect(jsonPath("$.result.email").value("admin@test.com"));
//    }
//
//    // --- CASE 2: Lỗi Validation (Thiếu trường bắt buộc) ---
//    @Test
//    @DisplayName("Create User: Trả về 400 Bad Request khi thiếu Username (Validate Fail)")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void createUser_InvalidRequest_Fail() throws Exception {
//        // Given: Request bị thiếu username (để trigger @NotBlank)
//        createUserRequest.setUsername("");
//
//        // When & Then
//        mockMvc.perform(post("/api/admin/users")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createUserRequest)))
//
//                // Mong đợi trả về 400 (Bad Request) do lỗi Validate
//                .andExpect(status().isBadRequest())
//
//                // Kiểm tra xem GlobalExceptionHandler có bắt lỗi và trả về đúng format không
//                // Code lỗi trong Enum ErrorCode.ARGUMENT_NOT_VALID là 1006
//                .andExpect(jsonPath("$.code").value(1006));
//    }
//
//    // --- CASE 3: Lỗi Phân quyền (403 Forbidden) ---
//    @Test
//    @DisplayName("Create User: Trả về 403 Forbidden nếu User không phải ADMIN")
//    @WithMockUser(username = "staff", roles = {"EDITOR"}) // Giả lập User chỉ có quyền EDITOR
//    void createUser_NoPermission_Forbidden() throws Exception {
//        // When & Then
//        mockMvc.perform(post("/api/admin/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createUserRequest)))
//
//                .andExpect(status().isForbidden()); // Mong đợi 403
//    }
//
//    @Test
//    @DisplayName("Update User: Trả về 200 OK và JSON đúng chuẩn khi updateUserRequest hợp lệ")
//    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Giả lập User có quyền ADMIN
//    void updateUser_ValidRequest_Success() throws Exception {
//        // Given: Khi gọi service thì trả về kết quả thành công
//
//        updateUserResponseApiResponse = ApiResponse.<UpdateUserResponse>builder()
//                .code(1000)
//                .message(ErrorCode.SUCCESS.getMessage())
//                .result(updateUserResponse)
//                .build();
//        when(userService.updateUser(Mockito.eq(1L), any(UpdateUserRequest.class))).thenReturn(updateUserResponseApiResponse);
//        // When & Then: Gọi API và kiểm tra
//        mockMvc.perform(put("/api/admin/users/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateUserRequest))) // Convert object request thành JSON string
//                // Kiểm tra HTTP Status
//                .andExpect(status().isOk())
//                // Kiểm tra nội dung JSON trả về (Dùng JsonPath)
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.id").value(1L))
//                .andExpect(jsonPath("$.result.username").value("testadmin"))
//                .andExpect(jsonPath("$.result.fullName").value("Admin Updated"))
//                .andExpect(jsonPath("$.result.email").value("lamthon@gmail.com"))
//                .andExpect(jsonPath("$.result.role").value("ADMIN"))
//                .andExpect(jsonPath("$.result.status").value("ACTIVE"));
//    }
//
//    @Test
//    @DisplayName("Update User: Trả về 400 Bad Request khi thiếu password (Validation Fail)")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void updateUser_ValidationFail() throws Exception {
//        // 1. Setup Invalid Data (Thiếu password)
//        Long userId = 1L;
//        UpdateUserRequest invalidRequest = UpdateUserRequest.builder()
//                .username("user")
//                .password("") // Rỗng -> Vi phạm @NotBlank
//                .fullName("Name")
//                .email("mail@test.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//
//        // 2. Perform
//        mockMvc.perform(put("/api/admin/users/{userId}", userId)
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value(1006)); // ErrorCode.ARGUMENT_NOT_VALID
//    }
//
//    @Test
//    @DisplayName("Update User: Trả về 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "staff", roles = {"EDITOR"}) // Role không đủ quyền
//    void updateUser_Forbidden() throws Exception {
//        // 1. Setup
//        Long userId = 1L;
//        UpdateUserRequest request = new UpdateUserRequest("u", "p", "n", "e", "r", "s");
//
//        // 2. Perform
//        mockMvc.perform(put("/api/admin/users/{userId}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và Status = INACTIVE")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void disableUser_Success() throws Exception {
//        // 1. GIVEN
//        Long userId = 123L;
//
//        // Mock Response từ Service
//        DisableUserResponse responseData = DisableUserResponse.builder()
//                .id(userId)
//                .username("john_doe")
//                .status("INACTIVE") // Kết quả mong đợi
//                .build();
//
//        ApiResponse<DisableUserResponse> apiResponse = ApiResponse.<DisableUserResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(responseData)
//                .build();
//
//        // Mock Service call
//        when(userService.disableUser(userId)).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        mockMvc.perform(patch("/api/admin/users/{userId}/disable", userId) // URL Template
//                        .with(csrf()) // Bắt buộc
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.id").value(userId))
//                .andExpect(jsonPath("$.result.status").value("INACTIVE"));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "editor", roles = {"EDITOR"})
//    void disableUser_Forbidden() throws Exception {
//        Long userId = 1L;
//
//        mockMvc.perform(patch("/api/admin/users/{userId}/disable", userId))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @DisplayName("Thất bại: 400 Bad Request nếu User không tồn tại (Service throw exception)")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void disableUser_NotFound() throws Exception {
//        Long userId = 999L;
//
//        // Giả lập Service ném lỗi USER_NOT_EXISTED
//        when(userService.disableUser(userId))
//                .thenThrow(new BaseException(ErrorCode.USER_NOT_EXISTED));
//
//        mockMvc.perform(patch("/api/admin/users/{userId}/disable", userId)
//                        .with(csrf()))
//
//                // ErrorCode.USER_NOT_EXISTED map với HttpStatus.BAD_REQUEST (400) trong GlobalExceptionHandler
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value(1001)); // Code 1001: User not existed
//    }
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và JSON User")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void getUser_Success() throws Exception {
//        // 1. GIVEN
//        Long userId = 1L;
//
//        // Dữ liệu giả lập trả về từ Service
//        GetUserResponse mockResponse = GetUserResponse.builder()
//                .id(userId)
//                .username("detail_user")
//                .email("detail@mail.com")
//                .role("EDITOR")
//                .status("ACTIVE")
//                .accountCode("ACCT1001")
//                .fullName("Detail User")
//                .createdAt(LocalDateTime.now().minusDays(10))
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        ApiResponse<GetUserResponse> apiResponse = ApiResponse.<GetUserResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(mockResponse)
//                .build();
//
//        // Mock Service
//        Mockito.when(userService.getUserById(userId)).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        mockMvc.perform(get("/api/admin/users/{userId}", userId) // URL Template
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk()) // HTTP 200
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.username").value("detail_user"))
//                .andExpect(jsonPath("$.result.email").value("detail@mail.com"));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 400 Bad Request khi không tìm thấy User")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void getUser_NotFound() throws Exception {
//        // 1. GIVEN
//        Long userId = 999L;
//
//        // Mock Service ném lỗi USER_NOT_EXISTED (Code 1001)
//        Mockito.when(userService.getUserById(userId))
//                .thenThrow(new BaseException(ErrorCode.USER_NOT_EXISTED));
//
//        // 2. WHEN & THEN
//        mockMvc.perform(get("/api/admin/users/{userId}", userId)
//                        .with(csrf()))
//
//                // Lưu ý: Code của bạn trả về 400 BAD_REQUEST cho lỗi này (check file ErrorCode.java)
//                // Nếu sau này bạn sửa ErrorCode thành HttpStatus.NOT_FOUND thì sửa dòng này thành .isNotFound()
//                .andExpect(status().isBadRequest())
//
//                .andExpect(jsonPath("$.code").value(1001)) // Mã lỗi nghiệp vụ
//                .andExpect(jsonPath("$.message").value("Người dùng không tồn tại"));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "staff", roles = {"EDITOR"}) // Role không đủ quyền
//    void getUser_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/1"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và số lượng")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void countProjectManagers_Success() throws Exception {
//        // 1. GIVEN
//        Long count = 10L;
//        CountProjectManagersResponse data = CountProjectManagersResponse.builder()
//                .projectManagersCount(count)
//                .build();
//
//        ApiResponse<CountProjectManagersResponse> apiResponse = ApiResponse.<CountProjectManagersResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(data)
//                .build();
//
//        // Mock Service
//        Mockito.when(userService.countProjectManagers()).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        mockMvc.perform(get("/api/admin/users/count-admin")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.projectManagersCount").value(10));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "staff", roles = {"EDITOR"}) // Role không đủ quyền
//    void countProjectManagers_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/count-admin"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và số lượng")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void countEditors_Success() throws Exception {
//        // 1. GIVEN
//        Long count = 42L;
//        CountEditorsResponse data = CountEditorsResponse.builder()
//                .editorsCount(count)
//                .build();
//
//        ApiResponse<CountEditorsResponse> apiResponse = ApiResponse.<CountEditorsResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(data)
//                .build();
//
//        // Mock Service
//        Mockito.when(userService.countEditors()).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        mockMvc.perform(get("/api/admin/users/count-editors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1000))
//                // Kiểm tra field editorsCount trong JSON trả về
//                .andExpect(jsonPath("$.result.editorsCount").value(42));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "editor", roles = {"EDITOR"}) // Role không đủ quyền
//    void countEditors_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/count-editors")
//                        .with(csrf()))
//                .andExpect(status().isForbidden());
//    }
//
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và số lượng")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void countModerators_Success() throws Exception {
//        // 1. GIVEN
//        Long count = 15L;
//        CountModeratorsResponse data = CountModeratorsResponse.builder()
//                .moderatorCount(count)
//                .build();
//
//        ApiResponse<CountModeratorsResponse> apiResponse = ApiResponse.<CountModeratorsResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(data)
//                .build();
//
//        // Mock Service trả về kết quả
//        Mockito.when(userService.countModerators()).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        mockMvc.perform(get("/api/admin/users/count-moderators")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1000))
//                // Kiểm tra field moderatorCount trong JSON trả về
//                .andExpect(jsonPath("$.result.moderatorCount").value(15));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "editor", roles = {"EDITOR"}) // Role không đủ quyền
//    void countModerators_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/count-moderators")
//                        .with(csrf()))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @DisplayName("Thành công: Trả về 200 OK và tổng số lượng")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void getUserCount_Success() throws Exception {
//        // 1. GIVEN
//        Long totalUsers = 150L;
//        GetUserCountResponse data = GetUserCountResponse.builder()
//                .userCount(totalUsers)
//                .build();
//
//        ApiResponse<GetUserCountResponse> apiResponse = ApiResponse.<GetUserCountResponse>builder()
//                .code(1000)
//                .message("Thành công")
//                .result(data)
//                .build();
//
//        // Mock Service
//        Mockito.when(userService.getUserCount()).thenReturn(apiResponse);
//
//        // 2. WHEN & THEN
//        // Lưu ý: Kiểm tra lại đường dẫn trong UserController của bạn xem có phải là "/user-count" không nhé
//        mockMvc.perform(get("/api/admin/users/user-count")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1000))
//                .andExpect(jsonPath("$.result.userCount").value(150));
//    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "manager", roles = {"PROJECT_MANAGER"}) // Role không đủ quyền
//    void getUserCount_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/user-count")
//                        .with(csrf()))
//                .andExpect(status().isForbidden());
//    }
//
////    @Test
////    @DisplayName("Thành công: Trả về 200 OK và cấu trúc Page")
////    @WithMockUser(username = "admin", roles = {"ADMIN"})
////    void getUsersByFilter_Success() throws Exception {
////        // 1. GIVEN
////        GetUserResponse userResponse = GetUserResponse.builder()
////                .id(1L)
////                .username("filtered_user")
////                .role("ADMIN")
////                .build();
////
////        // Tạo Page<GetUserResponse> giả
////        Page<GetUserResponse> mockPage = new PageImpl<>(
////                Collections.singletonList(userResponse)
////        );
////
////        ApiResponse<Page<GetUserResponse>> apiResponse = ApiResponse.<Page<GetUserResponse>>builder()
////                .code(1000)
////                .message("Thành công")
////                .result(mockPage)
////                .build();
////
////        // Mock Service: Chú ý dùng any() để bắt request
////        Mockito.when(userService.getUsersByFilter(any(UserFilterRequest.class), any(Integer.class)))
////                .thenReturn(apiResponse);
////
////        // 2. WHEN & THEN
////        mockMvc.perform(get("/api/admin/users/user-filter")
////                        .param("keyword", "admin") // Giả lập ?keyword=admin
////                        .param("role", "ADMIN")    // Giả lập &role=ADMIN
////                        .param("page", "0")        // Giả lập &page=0
////                        .with(csrf())
////                        .contentType(MediaType.APPLICATION_JSON))
////
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(1000))
////
////                // Kiểm tra cấu trúc Page của Spring Data
////                .andExpect(jsonPath("$.result.content[0].username").value("filtered_user"))
////                .andExpect(jsonPath("$.result.totalElements").value(1));
////        // Tùy phiên bản Spring, field này có thể là 'numberOfElements' hoặc nằm trong 'page' object
////    }
//
//    @Test
//    @DisplayName("Thất bại: 403 Forbidden nếu không phải ADMIN")
//    @WithMockUser(username = "staff", roles = {"EDITOR"})
//    void getUsersByFilter_Forbidden() throws Exception {
//        mockMvc.perform(get("/api/admin/users/user-filter")
//                        .param("page", "0"))
//                .andExpect(status().isForbidden());
//    }
//
//
//
//}
