package com.ocr.components.user.service;

import com.ocr.common.dto.response.ApiResponse;
import com.ocr.components.user.dto.request.CreateUserRequest;
import com.ocr.components.user.dto.request.UpdateUserRequest;
import com.ocr.components.user.dto.request.UserFilterRequest;
import com.ocr.components.user.dto.response.*;
import org.springframework.data.domain.Page;

public interface UserService {

    ApiResponse<CreateUserResponse> createUser(CreateUserRequest request);
    ApiResponse<UpdateUserResponse> updateUser(Long userId, UpdateUserRequest request);
    ApiResponse<DisableUserResponse> disableUser(Long userId);
    ApiResponse<GetUserResponse> getUserById(Long userId);
    ApiResponse<CountProjectManagersResponse> countProjectManagers();
    ApiResponse<CountEditorsResponse> countEditors();
    ApiResponse<CountModeratorsResponse> countModerators();
    ApiResponse<GetUserCountResponse> getUserCount();
    ApiResponse<Page<GetUserResponse>> getUsersByFilter(UserFilterRequest filterRequest, Integer page, Integer size);
}
