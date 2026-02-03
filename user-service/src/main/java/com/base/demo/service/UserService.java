package com.base.demo.service;

import com.base.demo.dto.request.CreateUserRequest;
import com.base.demo.dto.request.UpdateUserRequest;
import com.base.demo.dto.request.UserFilterRequest;
import com.base.demo.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    ApiResponse<CreateUserResponse> createUser(CreateUserRequest request);
    ApiResponse<UpdateUserResponse> updateUser(Long userId,UpdateUserRequest request);
    ApiResponse<DisableUserResponse> disableUser(Long userId);
    ApiResponse<GetUserResponse> getUserById(Long userId);
    ApiResponse<CountProjectManagersResponse> countProjectManagers();
    ApiResponse<CountEditorsResponse> countEditors();
    ApiResponse<CountModeratorsResponse> countModerators();
    ApiResponse<GetUserCountResponse> getUserCount();
    ApiResponse<Page<GetUserResponse>> getUsersByFilter(UserFilterRequest filterRequest, Integer page, Integer size);

}
