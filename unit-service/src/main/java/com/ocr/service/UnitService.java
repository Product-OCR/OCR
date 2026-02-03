package com.ocr.service;

import com.ocr.dto.request.CreateUnitRequest;
import com.ocr.dto.response.GetUnitsResponse;
import com.ocr.dto.request.UnitFilterRequest;
import com.ocr.dto.request.UpdateUnitRequest;
import com.ocr.dto.response.*;
import org.springframework.data.domain.Page;

public interface UnitService {
    ApiResponse<CreateUnitResponse> createUnit(CreateUnitRequest request);
    ApiResponse<UpdateUnitResponse> updateUnit(Long unitId, UpdateUnitRequest request);
    ApiResponse<DeleteUnitResponse> deleteUnit(Long unitId);
    ApiResponse<GetUnitResponse> getUnit(Long unitId);
    ApiResponse<Page<GetUnitsResponse>> getUnits(UnitFilterRequest unitFilterRequest, Integer page, Integer pageSize);
}
