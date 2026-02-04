package com.ocr.repository;

import com.ocr.model.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormTemplate, Long> {
}
