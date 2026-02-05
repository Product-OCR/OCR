package com.ocr.components.project.service;

import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.GetFormResponse;

import java.util.List;

public interface FormService {

    ApiResponse<List<GetFormResponse>> getAllForms();
}
