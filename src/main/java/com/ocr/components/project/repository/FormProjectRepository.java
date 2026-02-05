package com.ocr.components.project.repository;

import com.ocr.components.project.model.FormProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FormProjectRepository extends JpaRepository<FormProject, Long> {

    @Modifying
    @Query("DELETE FROM FormProject f WHERE f.projectId = :projectId")
    void deleteByProjectId(@Param("projectId") Long projectId);
}
