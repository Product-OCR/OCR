package com.base.demo.controller;

import com.base.demo.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/configs")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService service;

    @GetMapping("/{key}")
    public String getValue(@PathVariable String key) {
        return service.getValue(key);
    }
}
