package com.ocr.repository;

import com.ocr.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Long countByUnitId(Long unitId);
}
