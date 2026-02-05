package com.ocr.components.project.service;

import com.ocr.common.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.common.dto.request.CreateProjectRequest;
import com.ocr.common.dto.request.ProjectSearchRequest;
import com.ocr.common.dto.request.UpdateProjectRequest;
import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.CreateProjectResponse;
import com.ocr.common.dto.response.ProjectFilterResponse;
import com.ocr.common.dto.response.UpdateProjectResponse;
import org.springframework.data.domain.Page;

public interface ProjectService {

    ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(Long unitId);
    ApiResponse<CreateProjectResponse> createProject(CreateProjectRequest request);
    ApiResponse<UpdateProjectResponse> updateProject(Long projectId, UpdateProjectRequest request);
    ApiResponse<Page<ProjectFilterResponse>> searchProjects(ProjectSearchRequest request);
}
