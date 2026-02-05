package com.ocr.components.project.controller;

import com.ocr.common.dto.response.ApiResponse;
import com.ocr.common.dto.response.GetFormResponse;
import com.ocr.components.project.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<GetFormResponse>>> getAllForms() {
        return new ResponseEntity<>(formService.getAllForms(), HttpStatus.OK);
    }
}
