package com.uniquehire.rolemanagement.service;

import com.uniquehire.rolemanagement.dto.request.RoleRequest;
import com.uniquehire.rolemanagement.dto.response.RoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    RoleResponse updateRole(Long roleId, RoleRequest request);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(Long roleId);

    String deleteRole(Long roleId);

    String assignPermission(Long roleId, Long permissionId);

    String assignRoleToUser(Long userId, Long roleId);

    Page<RoleResponse> getRolesWithPagination(int page, int size);

}