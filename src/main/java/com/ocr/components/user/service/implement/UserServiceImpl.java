package com.ocr.components.user.service.implement;

import com.ocr.common.enums.ErrorCode;
import com.ocr.common.enums.Role;
import com.ocr.common.enums.UserStatus;
import com.ocr.common.exception.BaseException;
import com.ocr.common.model.Users;
import com.ocr.common.repository.UsersRepository;
import com.ocr.common.dto.response.ApiResponse;
import com.ocr.components.user.dto.request.CreateUserRequest;
import com.ocr.components.user.dto.request.UpdateUserRequest;
import com.ocr.components.user.dto.request.UserFilterRequest;
import com.ocr.components.user.dto.response.*;
import com.ocr.components.user.service.UserService;
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
        String encodePassword = passwordEncoder.encode(request.getPassword());
        Users newUser = Users.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodePassword)
                .role(Role.valueOf(request.getRole()))
                .status(UserStatus.ACTIVE)
                .build();
        usersRepository.save(newUser);
        CreateUserResponse response = CreateUserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .role(newUser.getRole().name())
                .status(newUser.getStatus().name())
                .build();
        return ApiResponse.<CreateUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));
        user.setStatus(UserStatus.valueOf(request.getStatus()));
        usersRepository.save(user);
        UpdateUserResponse response = UpdateUserResponse.builder()
                .id(user.getId())
                .accountCode(user.getAccountCode())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
        return ApiResponse.<UpdateUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<DisableUserResponse> disableUser(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTED));
        user.setStatus(UserStatus.INACTIVE);
        usersRepository.save(user);
        DisableUserResponse response = DisableUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
        return ApiResponse.<DisableUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<GetUserResponse> getUserById(Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTED));
        GetUserResponse response = GetUserResponse.builder()
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
        return ApiResponse.<GetUserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(response)
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<CountProjectManagersResponse> countProjectManagers() {
        Long count = usersRepository.countUsersByRole(Role.ADMIN);
        return ApiResponse.<CountProjectManagersResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(CountProjectManagersResponse.builder().projectManagersCount(count).build())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<CountEditorsResponse> countEditors() {
        Long count = usersRepository.countUsersByRole(Role.EDITOR);
        return ApiResponse.<CountEditorsResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(CountEditorsResponse.builder().editorsCount(count).build())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<CountModeratorsResponse> countModerators() {
        Long count = usersRepository.countUsersByRole(Role.PROJECT_MANAGER);
        return ApiResponse.<CountModeratorsResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(CountModeratorsResponse.builder().moderatorCount(count).build())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<GetUserCountResponse> getUserCount() {
        Long count = usersRepository.count();
        return ApiResponse.<GetUserCountResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(GetUserCountResponse.builder().userCount(count).build())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<Page<GetUserResponse>> getUsersByFilter(UserFilterRequest filterRequest, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by("createdAt").ascending()
        );
        Specification<Users> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(filterRequest.getKeyword())) {
                String keyword = "%" + filterRequest.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("username")), keyword),
                        cb.like(cb.lower(root.get("email")), keyword)
                ));
            }
            if (filterRequest.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), filterRequest.getRole()));
            }
            if (filterRequest.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterRequest.getStatus()));
            }
            if (filterRequest.getFromDate() != null && filterRequest.getToDate() != null) {
                predicates.add(cb.between(root.get("createdAt"), filterRequest.getFromDate(), filterRequest.getToDate()));
            }
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
        return ApiResponse.<Page<GetUserResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(usersRepository.findAll(spec, pageable).map(u -> GetUserResponse.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .accountCode(u.getAccountCode())
                        .fullName(u.getFullName())
                        .email(u.getEmail())
                        .role(u.getRole().name())
                        .status(u.getStatus().name())
                        .createdAt(u.getCreatedAt())
                        .updatedAt(u.getUpdatedAt())
                        .build()))
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }
}
