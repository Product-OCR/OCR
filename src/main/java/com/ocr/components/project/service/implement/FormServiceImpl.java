package com.ocr.components.project.service.implement;

import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.GetFormResponse;
import com.ocr.common.enums.ErrorCode;
import com.ocr.components.project.model.FormTemplate;
import com.ocr.components.project.repository.FormRepository;
import com.ocr.components.project.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;

    @Override
    public ApiResponse<List<GetFormResponse>> getAllForms() {
        List<FormTemplate> forms = formRepository.findAll();
        List<GetFormResponse> responses = new ArrayList<>();
        for (FormTemplate form : forms) {
            GetFormResponse response = new GetFormResponse();
            response.setId(form.getId());
            response.setFiled(form.getField());
            responses.add(response);
        }
        return ApiResponse.<List<GetFormResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(responses).build();
    }
}
