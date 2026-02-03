package com.ocr.service;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.response.ApiResponse;


public interface ProjectService {

    ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(Long unitId);
}
