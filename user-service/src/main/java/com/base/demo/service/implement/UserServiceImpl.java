package com.base.demo.service.implement;

import com.base.demo.dto.request.CreateUserRequest;
import com.base.demo.dto.request.UpdateUserRequest;
import com.base.demo.dto.request.UserFilterRequest;
import com.base.demo.dto.response.*;
import com.base.demo.enums.ErrorCode;
import com.base.demo.enums.Role;
import com.base.demo.enums.UserStatus;
import com.base.demo.exception.BaseException;
import com.base.demo.model.Users;
import com.base.demo.repository.UsersRepository;
import com.base.demo.service.UserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ApiResponse<CreateUserResponse> createUser(CreateUserRequest request) {

        if (usersRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (request.getRole() == null || request.getRole().isEmpty()) {
            request.setRole(Role.EDITOR.name());
        }
        String encodePassword = request.getPassword();
        encodePassword = passwordEncoder.encode(encodePassword);

        Users newUser = Users.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodePassword)
                .role(Role.valueOf(request.getRole()))
                .status(UserStatus.ACTIVE)
                .build();
        usersRepository.save(newUser);

        CreateUserResponse response = getCreateUserResponse(newUser);
        return ApiResponse.<CreateUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private CreateUserResponse getCreateUserResponse(Users newUser) {
        return CreateUserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .role(newUser.getRole().name())
                .status(newUser.getStatus().name())
                .build();
    }

    @Override
    public ApiResponse<UpdateUserResponse> updateUser(Long userId, UpdateUserRequest request) {

        Users user = usersRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTED));
        usersRepository.findByUsername(request.getUsername())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userId)) {
                        throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
                    }
                });
            user.setId(userId);
            String encodePassword = request.getPassword();
            encodePassword = passwordEncoder.encode(encodePassword);
            user.setPassword(encodePassword);
            user.setUsername(request.getUsername());
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setRole(Role.valueOf(request.getRole()));
            user.setStatus(UserStatus.valueOf(request.getStatus()));

        usersRepository.save(user);

        UpdateUserResponse response = getUpdateUserResponse(user);
        return ApiResponse.<UpdateUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private UpdateUserResponse getUpdateUserResponse(Users user) {
        return UpdateUserResponse.builder()
                .id(user.getId())
                .accountCode(user.getAccountCode())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }

    @Override
    public ApiResponse<DisableUserResponse> disableUser(Long userId) {

        Users user = usersRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTED));
        user.setStatus(UserStatus.INACTIVE);
        usersRepository.save(user);

        DisableUserResponse response = getDisableUserResponse(user);

        return ApiResponse.<DisableUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private DisableUserResponse getDisableUserResponse(Users user) {
        return DisableUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }

    @Override
    public ApiResponse<GetUserResponse> getUserById(Long userId) {

        Users users = usersRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTED));
        GetUserResponse response = getBuild(users);

        return ApiResponse.<GetUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private GetUserResponse getBuild(Users users) {
        return GetUserResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .accountCode(users.getAccountCode())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .role(users.getRole().name())
                .status(users.getStatus().name())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }

    @Override
    public ApiResponse<CountProjectManagersResponse> countProjectManagers() {
        Long count = usersRepository.countUsersByRole(Role.ADMIN);

        CountProjectManagersResponse response = CountProjectManagersResponse.builder()
                .projectManagersCount(count)
                .build();

        return ApiResponse.<CountProjectManagersResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<CountEditorsResponse> countEditors() {

        Long count = usersRepository.countUsersByRole(Role.EDITOR);

        CountEditorsResponse response = CountEditorsResponse.builder()
                .editorsCount(count)
                .build();

        return ApiResponse.<CountEditorsResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<CountModeratorsResponse> countModerators() {

        Long count = usersRepository.countUsersByRole(Role.PROJECT_MANAGER);

        CountModeratorsResponse response = CountModeratorsResponse.builder()
                .moderatorCount(count)
                .build();

        return ApiResponse.<CountModeratorsResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<GetUserCountResponse> getUserCount() {

        Long count = usersRepository.count();

        GetUserCountResponse response = GetUserCountResponse.builder()
                .userCount(count)
                .build();

        return ApiResponse.<GetUserCountResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<Page<GetUserResponse>> getUsersByFilter(UserFilterRequest filterRequest, Integer page, Integer size) {
        // 1. Quy tắc: Default 20 items và Sort Recently Created (Giảm dần theo createdAt)
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by("createdAt").ascending()
        );

        // 2. Logic lọc động theo Figma
        Specification<Users> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Thanh search tương đối (Keyword)
            if (StringUtils.hasText(filterRequest.getKeyword())) {
                String keyword = "%" + filterRequest.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("username")), keyword),
                        cb.like(cb.lower(root.get("email")), keyword)
                ));
            }

            // Filter vai trò
            if (filterRequest.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), filterRequest.getRole()));
            }

            // Filter trạng thái (Nếu Entity của cậu chưa có field status thì nên thêm vào nhé)
            if (filterRequest.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterRequest.getStatus()));
            }

            // Từ ngày đến ngày (Ngày tạo)
            if (filterRequest.getFromDate() != null && filterRequest.getToDate() != null) {
                predicates.add(cb.between(root.get("createdAt"), filterRequest.getFromDate(), filterRequest.getToDate()));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };

        return ApiResponse.<Page<GetUserResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(usersRepository.findAll(spec, pageable).map(this::getBuild))
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }
}
