package com.ocr.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "config-service",
        url = "${services.config-service.url}"
)
public interface SysConfigClient {

    @GetMapping("/api/internal/configs/{key}")
    String getValue(@PathVariable("key") String key);
}
