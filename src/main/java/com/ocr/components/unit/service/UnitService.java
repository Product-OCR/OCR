package com.ocr.components.unit.service;

import com.ocr.common.dto.request.UnitFilterRequest;
import com.ocr.common.dto.response.GetUnitsResponse;
import com.ocr.components.unit.dto.request.CreateUnitRequest;
import com.ocr.components.unit.dto.request.UpdateUnitRequest;
import com.ocr.components.unit.dto.response.*;
import com.ocr.common.dto.response.ApiResponse;
import org.springframework.data.domain.Page;

public interface UnitService {

    ApiResponse<CreateUnitResponse> createUnit(CreateUnitRequest request);
    ApiResponse<UpdateUnitResponse> updateUnit(Long unitId, UpdateUnitRequest request);
    ApiResponse<DeleteUnitResponse> deleteUnit(Long unitId);
    ApiResponse<GetUnitResponse> getUnit(Long unitId);
    ApiResponse<Page<GetUnitsResponse>> getUnits(UnitFilterRequest unitFilterRequest, Integer page, Integer pageSize);
}
