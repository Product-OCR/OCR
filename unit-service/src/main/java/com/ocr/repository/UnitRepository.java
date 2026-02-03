package com.ocr.repository;

import com.ocr.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Boolean existsUnitByUnitName(String name);

    Boolean existsUnitById(Long id);

    String getUnitById(Long id);
}
