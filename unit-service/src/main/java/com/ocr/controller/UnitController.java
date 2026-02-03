package com.ocr.controller;

import com.ocr.dto.request.CreateUnitRequest;
import com.ocr.dto.request.UpdateUnitRequest;
import com.ocr.dto.response.*;
import com.ocr.model.Unit;
import com.ocr.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
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
    public ResponseEntity<ApiResponse<DeleteUnitResponse>> deleteUnit(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.deleteUnit(unitId), HttpStatus.OK);
    }

    @Operation(summary = "Lấy thông tin đơn vị theo ID")
    @GetMapping("/{unitId}")
    public ResponseEntity<ApiResponse<GetUnitResponse>> getUnitById(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.getUnit(unitId), HttpStatus.OK);
    }
}
