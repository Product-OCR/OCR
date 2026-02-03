package com.ocr.repository;

import com.ocr.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    Boolean existsUnitByUnitName(String name);

    Boolean existsUnitById(Long id);

    String getUnitById(Long id);
}
