package com.ocr.common.repository;

import com.ocr.common.enums.Role;
import com.ocr.common.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = :role")
    long countUsersByRole(@Param("role") Role role);
}
