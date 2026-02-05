package com.ocr.components.unit.repository;

import com.ocr.components.unit.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    Boolean existsUnitByUnitName(String name);
    Boolean existsUnitById(Long id);
    String getUnitById(Long id);
}
