//package com.base.demo.service;
//
//import com.base.demo.dto.request.CreateUserRequest;
//import com.base.demo.dto.request.UpdateUserRequest;
//import com.base.demo.dto.request.UserFilterRequest;
//import com.base.demo.dto.response.*;
//import com.base.demo.enums.ErrorCode;
//import com.base.demo.enums.Role;
//import com.base.demo.enums.UserStatus;
//import com.base.demo.exception.BaseException;
//import com.base.demo.model.Users;
//import com.base.demo.repository.UsersRepository;
//import com.base.demo.service.implement.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private CreateUserRequest request;
//    private Users user;
//
//    @BeforeEach
//    void setUp() {
//        // Chuẩn bị dữ liệu mẫu đầu vào
//        request = CreateUserRequest.builder()
//                .username("testuser")
//                .password("password123")
//                .fullName("Test User")
//                .email("test@gmail.com")
//                .role("ADMIN")
//                .status("ACTIVE")
//                .build();
//
//        // Chuẩn bị Entity mẫu sau khi save
//        user = Users.builder()
//                .id(1L)
//                .username("testuser")
//                .password("encodedPassword")
//                .fullName("Test User")
//                .email("test@gmail.com")
//                .role(Role.ADMIN)
//                .status(UserStatus.ACTIVE)
//                .build();
//    }
//
//    // --- CASE 1: Tạo user thành công ---
//    @Test
//    @DisplayName("Create User: Thành công khi dữ liệu hợp lệ")
//    void createUser_WhenValidRequest_ShouldReturnSuccess() {
//        // 1. GIVEN (Giả lập hành vi)
//        // Giả lập chưa có user nào trùng tên
//        when(usersRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
//        // Giả lập encode password
//        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
//        // Giả lập lưu vào DB
//        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> {
//            Users savedUser = invocation.getArgument(0);
//            savedUser.setId(1L); // Giả lập DB sinh ID
//            return savedUser;
//        });
//
//        // 2. WHEN (Thực thi)
//        ApiResponse<CreateUserResponse> response = userService.createUser(request);
//
//        // 3. THEN (Kiểm tra kết quả)
//        assertThat(response).isNotNull();
//        assertThat(response.getCode()).isEqualTo(1000); // Check ErrorCode.SUCCESS
//        assertThat(response.getResult().getUsername()).isEqualTo("testuser");
//        assertThat(response.getResult().getRole()).isEqualTo("ADMIN");
//
//        // Verify xem các hàm mock có được gọi không
//        verify(usersRepository, times(1)).save(any(Users.class));
//        verify(passwordEncoder, times(1)).encode("password123");
//    }
//
//    // --- CASE 2: Lỗi trùng tên đăng nhập ---
//    @Test
//    @DisplayName("Create User: Ném lỗi khi Username đã tồn tại")
//    void createUser_WhenUsernameExists_ShouldThrowException() {
//        // 1. GIVEN
//        // Giả lập tìm thấy 1 user đã tồn tại
//        when(usersRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
//
//        // 2. WHEN & THEN
//        // Kiểm tra xem có ném ra BaseException không
//        BaseException exception = assertThrows(BaseException.class, () -> {
//            userService.createUser(request);
//        });
//
//        // Kiểm tra mã lỗi bên trong Exception
//        assertThat(exception.getCode()).isEqualTo(ErrorCode.USERNAME_ALREADY_EXISTS.getCode());
//
//        // Đảm bảo không bao giờ gọi hàm save
//        verify(usersRepository, never()).save(any(Users.class));
//    }
//
//    // --- CASE 3: Tự động gán Role mặc định ---
//    @Test
//    @DisplayName("Create User: Tự động set Role EDITOR nếu request thiếu Role")
//    void createUser_WhenRoleMissing_ShouldSetDefaultRole() {
//        // 1. GIVEN
//        request.setRole(null); // Giả sử client không gửi role lên
//
//        when(usersRepository.findByUsername(anyString())).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // 2. WHEN
//        ApiResponse<CreateUserResponse> response = userService.createUser(request);
//
//        // 3. THEN
//        assertThat(response.getResult().getRole()).isEqualTo("EDITOR"); // Kiểm tra logic default role
//    }
//
//    @Test
//    @DisplayName("Update User: Thành công khi dữ liệu hợp lệ")
//    void updateUser_WhenValidRequest_ShouldReturnSuccess() {
//        // 1. GIVEN
//        Long userId = 1L;
//        UpdateUserRequest updateRequest = UpdateUserRequest.builder()
//                .username("new_username")
//                .password("newPass123")
//                .fullName("New Name")
//                .email("new@mail.com")
//                .role("EDITOR")
//                .status("INACTIVE")
//                .build();
//
//        // Mock tìm thấy user cũ trong DB
//        Users existingUser = Users.builder()
//                .id(1L)
//                .username("old_username")
//                .password("oldPass")
//                .build();
//
//        when(usersRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//
//        // Mock tìm username mới: Trả về rỗng (chưa ai dùng) HOẶC chính user đó
//        when(usersRepository.findByUsername("new_username")).thenReturn(Optional.empty());
//
//        when(passwordEncoder.encode("newPass123")).thenReturn("encodedNewPass");
//        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // 2. WHEN
//        ApiResponse<UpdateUserResponse> response = userService.updateUser(userId, updateRequest);
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getUsername()).isEqualTo("new_username");
//        assertThat(response.getResult().getStatus()).isEqualTo("INACTIVE"); // Check đã update field
//
//        verify(usersRepository).save(existingUser);
//    }
//
//    // CASE 2: Lỗi không tìm thấy ID User
//    @Test
//    @DisplayName("Update User: Lỗi khi ID không tồn tại")
//    void updateUser_WhenIdNotFound_ShouldThrowException() {
//        // 1. GIVEN
//        Long userId = 99L;
//        UpdateUserRequest updateRequest = new UpdateUserRequest(); // Data không quan trọng vì fail ngay bước 1
//
//        when(usersRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // 2. WHEN & THEN
//        BaseException exception = assertThrows(BaseException.class, () ->
//                userService.updateUser(userId, updateRequest)
//        );
//
//        assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED.getCode());
//    }
//
//    // CASE 3: Lỗi trùng tên đăng nhập với người khác
//    @Test
//    @DisplayName("Update User: Lỗi khi Username mới đã tồn tại (của người khác)")
//    void updateUser_WhenUsernameConflict_ShouldThrowException() {
//        // 1. GIVEN
//        Long userId = 1L;
//        UpdateUserRequest updateRequest = UpdateUserRequest.builder()
//                .username("duplicate_name")
//                .build();
//
//        Users currentUser = Users.builder().id(1L).username("my_name").build();
//
//        // Giả lập tìm thấy 1 user KHÁC (id=2) đang dùng username này
//        Users anotherUser = Users.builder().id(2L).username("duplicate_name").build();
//
//        when(usersRepository.findById(userId)).thenReturn(Optional.of(currentUser));
//        when(usersRepository.findByUsername("duplicate_name")).thenReturn(Optional.of(anotherUser));
//
//        // 2. WHEN & THEN
//        BaseException exception = assertThrows(BaseException.class, () ->
//                userService.updateUser(userId, updateRequest)
//        );
//
//        assertThat(exception.getCode()).isEqualTo(ErrorCode.USERNAME_ALREADY_EXISTS.getCode());
//    }
//
//    @Test
//    @DisplayName("Disable User: Thành công (Soft Delete)")
//    void disableUser_WhenUserExists_ShouldSuccess() {
//        // 1. GIVEN
//        Long userId = 1L;
//        // User ban đầu đang ACTIVE
//        Users existingUser = Users.builder()
//                .id(userId)
//                .username("user_active")
//                .role(Role.EDITOR)
//                .status(UserStatus.ACTIVE)
//                .build();
//
//        when(usersRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // 2. WHEN
//        ApiResponse<DisableUserResponse> response = userService.disableUser(userId);
//
//        // 3. THEN
//        // Kiểm tra Status đã đổi sang INACTIVE chưa
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getStatus()).isEqualTo("INACTIVE");
//
//        // Quan trọng: Kiểm tra logic set status trong object user trước khi save
//        assertThat(existingUser.getStatus()).isEqualTo(UserStatus.INACTIVE);
//        verify(usersRepository).save(existingUser);
//    }
//
//    @Test
//    @DisplayName("Disable User: Lỗi khi ID không tồn tại")
//    void disableUser_WhenIdNotFound_ShouldThrowException() {
//        // 1. GIVEN
//        Long userId = 99L;
//        when(usersRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // 2. WHEN & THEN
//        BaseException exception = assertThrows(BaseException.class, () ->
//                userService.disableUser(userId)
//        );
//
//        assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED.getCode());
//        verify(usersRepository, never()).save(any(Users.class));
//    }
//
//    @Test
//    @DisplayName("Get User By ID: Thành công - Trả về đúng thông tin")
//    void getUserById_WhenExists_ShouldReturnUser() {
//        // 1. GIVEN
//        Long userId = 1L;
//        Users existingUser = Users.builder()
//                .id(userId)
//                .username("test_get")
//                .fullName("Test Get")
//                .email("get@mail.com")
//                .role(Role.ADMIN)
//                .status(UserStatus.ACTIVE)
//                .build();
//        existingUser.setCreatedAt(LocalDateTime.now());
//        existingUser.setUpdatedAt(LocalDateTime.now());
//
//        // Mock hành vi Repository tìm thấy user
//        when(usersRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//
//        // 2. WHEN
//        ApiResponse<GetUserResponse> response = userService.getUserById(userId);
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000); // SUCCESS
//        assertThat(response.getResult().getId()).isEqualTo(userId);
//        assertThat(response.getResult().getUsername()).isEqualTo("test_get");
//        assertThat(response.getResult().getRole()).isEqualTo("ADMIN");
//
//        // Kiểm tra xem đã map đúng các trường thời gian chưa
//        assertThat(response.getResult().getCreatedAt()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Get User By ID: Lỗi - Ném exception khi không tìm thấy")
//    void getUserById_WhenNotFound_ShouldThrowException() {
//        // 1. GIVEN
//        Long userId = 999L;
//        // Mock hành vi Repository trả về rỗng
//        when(usersRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // 2. WHEN & THEN
//        BaseException exception = assertThrows(BaseException.class, () ->
//                userService.getUserById(userId)
//        );
//
//        // Kiểm tra đúng mã lỗi USER_NOT_EXISTED (1001)
//        assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED.getCode());
//    }
//
//    @Test
//    @DisplayName("Count Admin: Thành công - Trả về đúng số lượng")
//    void countProjectManagers_ShouldReturnCorrectCount() {
//        // 1. GIVEN
//        Long mockCount = 15L;
//
//        // Mock Repository trả về số lượng là 15 khi đếm Role ADMIN
//        // Lưu ý: Logic trong UserServiceImpl của bạn đang gọi Role.ADMIN
//        when(usersRepository.countUsersByRole(Role.ADMIN)).thenReturn(mockCount);
//
//        // 2. WHEN
//        ApiResponse<CountProjectManagersResponse> response = userService.countProjectManagers();
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getProjectManagersCount()).isEqualTo(mockCount);
//
//        // Verify repository được gọi đúng tham số Role.ADMIN
//        verify(usersRepository).countUsersByRole(Role.ADMIN);
//    }
//
//    @Test
//    @DisplayName("Count Editors: Thành công - Trả về đúng số lượng EDITOR")
//    void countEditors_ShouldReturnCorrectCount() {
//        // 1. GIVEN
//        Long mockCount = 20L;
//
//        // Mock Repository trả về 20 khi đếm Role EDITOR
//        when(usersRepository.countUsersByRole(Role.EDITOR)).thenReturn(mockCount);
//
//        // 2. WHEN
//        ApiResponse<CountEditorsResponse> response = userService.countEditors();
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getEditorsCount()).isEqualTo(mockCount);
//
//        // Verify repository được gọi đúng tham số Role.EDITOR
//        verify(usersRepository).countUsersByRole(Role.EDITOR);
//    }
//
//    @Test
//    @DisplayName("Count Moderators: Thành công - Trả về đúng số lượng PROJECT_MANAGER")
//    void countModerators_ShouldReturnCorrectCount() {
//        // 1. GIVEN
//        Long mockCount = 15L;
//
//        // Mock Repository trả về 15 khi đếm Role PROJECT_MANAGER
//        // Logic trong UserServiceImpl đang dùng Role.PROJECT_MANAGER
//        when(usersRepository.countUsersByRole(Role.PROJECT_MANAGER)).thenReturn(mockCount);
//
//        // 2. WHEN
//        ApiResponse<CountModeratorsResponse> response = userService.countModerators();
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getModeratorCount()).isEqualTo(mockCount);
//
//        // Verify repository được gọi đúng tham số
//        verify(usersRepository).countUsersByRole(Role.PROJECT_MANAGER);
//    }
//
//    @Test
//    @DisplayName("Get User Count: Thành công - Trả về tổng số người dùng")
//    void getUserCount_ShouldReturnCorrectCount() {
//        // 1. GIVEN
//        Long mockTotal = 150L;
//
//        // Mock Repository: hàm count() của JpaRepository trả về long
//        when(usersRepository.count()).thenReturn(mockTotal);
//
//        // 2. WHEN
//        ApiResponse<GetUserCountResponse> response = userService.getUserCount();
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//        assertThat(response.getResult().getUserCount()).isEqualTo(mockTotal);
//
//        // Verify gọi đúng hàm count()
//        verify(usersRepository).count();
//    }
//
//    @Test
//    @DisplayName("Get Users By Filter: Thành công - Trả về danh sách phân trang")
//    void getUsersByFilter_ShouldReturnPagedResult() {
//        // 1. GIVEN
//        int page = 0;
//        UserFilterRequest filterRequest = UserFilterRequest.builder()
//                .keyword("test")
//                .role(Role.ADMIN)
//                .status(UserStatus.ACTIVE)
//                .build();
//
//        // Tạo dữ liệu giả từ DB (Entity)
//        Users userEntity = Users.builder()
//                .id(1L)
//                .username("test_user")
//                .fullName("Test User")
//                .role(Role.ADMIN)
//                .status(UserStatus.ACTIVE)
//                .build();
//
//        // Tạo Page<Users> giả
//        List<Users> usersList = Collections.singletonList(userEntity);
//        Page<Users> mockPage = new PageImpl<>(usersList);
//
//        // Mock Repository: Chấp nhận bất kỳ Specification và Pageable nào
//        when(usersRepository.findAll(any(Specification.class), any(Pageable.class)))
//                .thenReturn(mockPage);
//
//        // 2. WHEN
//        ApiResponse<Page<GetUserResponse>> response = userService.getUsersByFilter(filterRequest, page);
//
//        // 3. THEN
//        assertThat(response.getCode()).isEqualTo(1000);
//
//        // Kiểm tra nội dung Page trả về
//        Page<GetUserResponse> resultPage = response.getResult();
//        assertThat(resultPage.getTotalElements()).isEqualTo(1);
//        assertThat(resultPage.getContent().get(0).getUsername()).isEqualTo("test_user");
//
//        // Verify repository được gọi
//        verify(usersRepository).findAll(any(Specification.class), any(Pageable.class));
//    }
//
//    @Test
//    @DisplayName("Get Users By Filter: Thành công - Trả về danh sách rỗng nếu không tìm thấy")
//    void getUsersByFilter_WhenNoMatch_ShouldReturnEmptyPage() {
//        // 1. GIVEN
//        UserFilterRequest filterRequest = new UserFilterRequest();
//
//        // Mock Page rỗng
//        Page<Users> emptyPage = new PageImpl<>(Collections.emptyList());
//
//        when(usersRepository.findAll(any(Specification.class), any(Pageable.class)))
//                .thenReturn(emptyPage);
//
//        // 2. WHEN
//        ApiResponse<Page<GetUserResponse>> response = userService.getUsersByFilter(filterRequest, 0);
//
//        // 3. THEN
//        assertThat(response.getResult().getTotalElements()).isEqualTo(0);
//        assertThat(response.getResult().getContent()).isEmpty();
//    }
//
//
//}
