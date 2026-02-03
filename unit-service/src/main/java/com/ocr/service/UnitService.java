package com.ocr.service;

import com.ocr.dto.request.CreateUnitRequest;
import com.ocr.dto.request.UpdateUnitRequest;
import com.ocr.dto.response.*;

public interface UnitService {
    ApiResponse<CreateUnitResponse> createUnit(CreateUnitRequest request);
    ApiResponse<UpdateUnitResponse> updateUnit(Long unitId, UpdateUnitRequest request);
    ApiResponse<DeleteUnitResponse> deleteUnit(Long unitId);
    ApiResponse<GetUnitResponse> getUnit(Long unitId);
}
