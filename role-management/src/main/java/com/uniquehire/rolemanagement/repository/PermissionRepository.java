package com.uniquehire.rolemanagement.repository;

import com.uniquehire.rolemanagement.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
