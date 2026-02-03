package com.base.demo.repository;

import com.base.demo.enums.Role;
import com.base.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = :role")
    long countUsersByRole(@Param("role") Role role);
}
