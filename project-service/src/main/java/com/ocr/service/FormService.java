package com.ocr.service;

import com.ocr.dto.response.ApiResponse;
import com.ocr.dto.response.GetFormResponse;

import java.util.List;

public interface FormService {

    ApiResponse<List<GetFormResponse>> getAllForms();
}
