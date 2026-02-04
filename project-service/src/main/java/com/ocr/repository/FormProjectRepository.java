package com.ocr.repository;

import com.ocr.model.FormProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FormProjectRepository extends JpaRepository<FormProject, Long> {

    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
    }
