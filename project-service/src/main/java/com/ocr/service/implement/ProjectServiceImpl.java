package com.ocr.service.implement;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.response.ApiResponse;
import com.ocr.enums.ErrorCode;
import com.ocr.repository.ProjectRepository;
import com.ocr.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(Long unitId) {
        CountProjectsByUnitIdResponse response = new CountProjectsByUnitIdResponse();
        response.setCount(projectRepository.countByUnitId(unitId));

        return ApiResponse.<CountProjectsByUnitIdResponse> builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(response).build();
    }
}
