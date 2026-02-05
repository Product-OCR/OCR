package com.ocr.components.unit.controller;

import com.ocr.common.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.common.dto.request.UnitFilterRequest;
import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.GetUnitsResponse;
import com.ocr.components.project.service.ProjectService;
import com.ocr.components.unit.dto.request.CreateUnitRequest;
import com.ocr.components.unit.dto.request.UpdateUnitRequest;
import com.ocr.components.unit.dto.response.*;
import com.ocr.components.unit.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final ProjectService projectService;

    @Operation(summary = "Tạo mới đơn vị")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateUnitResponse>> addUnit(@Valid @RequestBody CreateUnitRequest request) {
        return new ResponseEntity<>(unitService.createUnit(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Chỉnh sửa đơn vị")
    @PutMapping("/{unitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UpdateUnitResponse>> editUnit(@PathVariable Long unitId, @RequestBody UpdateUnitRequest request) {
        return new ResponseEntity<>(unitService.updateUnit(unitId, request), HttpStatus.OK);
    }

    @Operation(summary = "Xoá đơn vị")
    @DeleteMapping("/{unitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeleteUnitResponse>> deleteUnit(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.deleteUnit(unitId), HttpStatus.OK);
    }

    @Operation(summary = "Lấy thông tin đơn vị theo ID")
    @GetMapping("/{unitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GetUnitResponse>> getUnitById(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.getUnit(unitId), HttpStatus.OK);
    }

    @Operation(summary = "Lấy danh sách đơn vị")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<GetUnitsResponse>>> getUnits(
            @ParameterObject UnitFilterRequest request,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        return new ResponseEntity<>(unitService.getUnits(request, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/test/{unitId}")
    public ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(@PathVariable Long unitId) {
        return projectService.countByUnitId(unitId);
    }
}
