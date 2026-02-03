package com.ocr.client;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "project-service", url = "${services.project-service.url}")
public interface ProjectClient {

    @GetMapping("/api/admin/projects/count/{unitId}")
    ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(@PathVariable Long unitId);
}
