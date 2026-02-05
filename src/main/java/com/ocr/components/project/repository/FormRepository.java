package com.ocr.components.project.repository;

import com.ocr.components.project.model.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<FormTemplate, Long> {
}
