package com.ocr.service.implement;

import com.ocr.dto.response.ApiResponse;
import com.ocr.dto.response.GetFormResponse;
import com.ocr.enums.ErrorCode;
import com.ocr.model.FormTemplate;
import com.ocr.repository.FormRepository;
import com.ocr.service.FormService;
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

        extracted(forms, responses);

        return ApiResponse.<List<GetFormResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(responses).build();

    }

    private void extracted(List<FormTemplate> forms, List<GetFormResponse> responses) {
        for (FormTemplate form : forms) {
            GetFormResponse response = new GetFormResponse();
            response.setId(form.getId());
            response.setFiled(form.getField());
            responses.add(response);
        }
    }
}
