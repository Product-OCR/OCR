package com.ocr.controller;

import com.ocr.client.ProjectClient;
import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.request.CreateUnitRequest;
import com.ocr.dto.response.GetUnitsResponse;
import com.ocr.dto.request.UnitFilterRequest;
import com.ocr.dto.request.UpdateUnitRequest;
import com.ocr.dto.response.*;
import com.ocr.model.Unit;
import com.ocr.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UnitController {

    private final UnitService unitService;

    @Operation(summary = "Tạo mới đơn vị")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateUnitResponse>> addUnit(@Valid @RequestBody CreateUnitRequest request) {
        return new ResponseEntity<>(unitService.createUnit(request), HttpStatus.CREATED);
    }


    @Operation(summary = "Chỉnh sửa đơn vị")
    @PutMapping("/{unitId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ApiResponse<UpdateUnitResponse>> editUnit(@PathVariable Long unitId, @RequestBody UpdateUnitRequest request) {
        return new ResponseEntity<>(unitService.updateUnit(unitId, request), HttpStatus.OK);
    }

    @Operation(summary = "Xoá đơn vị")
    @DeleteMapping("/{unitId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ApiResponse<DeleteUnitResponse>> deleteUnit(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.deleteUnit(unitId), HttpStatus.OK);
    }

    @Operation(summary = "Lấy thông tin đơn vị theo ID")
    @GetMapping("/{unitId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ApiResponse<GetUnitResponse>> getUnitById(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.getUnit(unitId), HttpStatus.OK);
    }

    @Operation(summary = "")
    @GetMapping("/list")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ApiResponse<Page<GetUnitsResponse>>> getUnits(@ParameterObject UnitFilterRequest request
    , @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    , @RequestParam(name = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        return new ResponseEntity<>(unitService.getUnits(request, page, pageSize), HttpStatus.OK);
    }
    private final ProjectClient projectClient;
    @GetMapping("/test/{unitId}")
    public ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(@PathVariable Long unitId) {
        return projectClient.countByUnitId(unitId);
    }
}
