package com.ocr.components.project.controller;

import com.ocr.common.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.common.dto.request.CreateProjectRequest;
import com.ocr.common.dto.request.ProjectSearchRequest;
import com.ocr.common.dto.request.UpdateProjectRequest;
import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.CreateProjectResponse;
import com.ocr.common.dto.response.ProjectFilterResponse;
import com.ocr.common.dto.response.UpdateProjectResponse;
import com.ocr.components.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/{unitId}")
    public ResponseEntity<ApiResponse<CountProjectsByUnitIdResponse>> countByUnitId(@PathVariable Long unitId) {
        return new ResponseEntity<>(projectService.countByUnitId(unitId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateProjectResponse>> createProject(@RequestBody CreateProjectRequest request) {
        return new ResponseEntity<>(projectService.createProject(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{projectId}")
    public ResponseEntity<ApiResponse<UpdateProjectResponse>> updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectRequest request) {
        return new ResponseEntity<>(projectService.updateProject(projectId, request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    @Operation(summary = "Lấy danh sách dự án phân trang")
    public ResponseEntity<ApiResponse<Page<ProjectFilterResponse>>> getProjectList(@ParameterObject ProjectSearchRequest request) {
        return new ResponseEntity<>(projectService.searchProjects(request), HttpStatus.OK);
    }
}
