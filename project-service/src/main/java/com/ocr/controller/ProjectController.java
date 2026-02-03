package com.ocr.controller;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.response.ApiResponse;
import com.ocr.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/projects")
@EnableMethodSecurity
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/{unitId}")
    public ResponseEntity<ApiResponse<CountProjectsByUnitIdResponse>> countByUnitId(@PathVariable Long unitId) {
        return new ResponseEntity<>(projectService.countByUnitId(unitId), HttpStatus.OK);
    }

}
