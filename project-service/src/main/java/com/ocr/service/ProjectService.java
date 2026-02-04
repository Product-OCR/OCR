package com.ocr.service;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.request.CreateProjectRequest;
import com.ocr.dto.request.ProjectSearchRequest;
import com.ocr.dto.request.UpdateProjectRequest;
import com.ocr.dto.response.ApiResponse;
import com.ocr.dto.response.CreateProjectResponse;
import com.ocr.dto.response.ProjectFilterResponse;
import com.ocr.dto.response.UpdateProjectResponse;
import org.springframework.data.domain.Page;


public interface ProjectService {

    ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(Long unitId);
    ApiResponse<CreateProjectResponse> createProject(CreateProjectRequest request);
    ApiResponse<UpdateProjectResponse> updateProject(Long projectId, UpdateProjectRequest request);
    ApiResponse<Page<ProjectFilterResponse>> searchProjects(ProjectSearchRequest request);
}
