package com.ocr.components.project.repository;

import com.ocr.components.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    Long countByUnitId(Long unitId);

    @Modifying
    @Query(value = "UPDATE units SET total_projects = COALESCE(total_projects, 0) + 1 WHERE id = :unitId", nativeQuery = true)
    void incrementUnitProjectCount(@Param("unitId") Long unitId);

    @Query(value = "SELECT id, unit_name FROM units WHERE id IN :ids", nativeQuery = true)
    List<Object[]> findUnitNamesByUnitIds(@Param("ids") Set<Long> ids);

    @Query(value = "SELECT fp.project_id, ft.field FROM form_project fp " +
            "JOIN form ft ON fp.form_id = ft.id " +
            "WHERE fp.project_id IN :projectIds", nativeQuery = true)
    List<Object[]> findTemplateNamesByProjectIds(@Param("projectIds") Set<Long> projectIds);

    @Query(value = "SELECT id, full_name FROM users WHERE id IN :ids", nativeQuery = true)
    List<Object[]> findManagerNamesByIds(@Param("ids") Set<Long> ids);
}
