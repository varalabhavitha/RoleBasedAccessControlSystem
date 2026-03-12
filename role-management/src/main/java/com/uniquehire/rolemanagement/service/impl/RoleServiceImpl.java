package com.uniquehire.rolemanagement.service.impl;

import com.uniquehire.rolemanagement.dto.request.RoleRequest;
import com.uniquehire.rolemanagement.dto.response.RoleResponse;
import com.uniquehire.rolemanagement.entity.Permission;
import com.uniquehire.rolemanagement.entity.Role;
import com.uniquehire.rolemanagement.entity.User;
import com.uniquehire.rolemanagement.enums.Status;
import com.uniquehire.rolemanagement.repository.PermissionRepository;
import com.uniquehire.rolemanagement.repository.RoleRepository;
import com.uniquehire.rolemanagement.repository.UserRepository;
import com.uniquehire.rolemanagement.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {

        Role role = Role.builder()
                .roleName(request.getRoleName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Status.ACTIVE)
                .createdBy(request.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .build();

        Role savedRole = roleRepository.save(role);

        return map(savedRole);
    }

    @Override
    public RoleResponse getRoleById(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return map(role);
    }

    @Override
    public RoleResponse updateRole(Long roleId, RoleRequest request) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setStatus(request.getStatus() != null ? request.getStatus() : role.getStatus());
        role.setUpdatedBy(request.getUpdatedBy());
        role.setUpdatedAt(LocalDateTime.now());

        Role updatedRole = roleRepository.save(role);

        return map(updatedRole);
    }

    @Override
    public String deleteRole(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setStatus(Status.DELETED);
        role.setUpdatedAt(LocalDateTime.now());

        roleRepository.save(role);

        return "Role deleted successfully";
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoleResponse> getRolesWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return roleRepository.findAll(pageable)
                .map(this::map);
    }

    @Override
    public String assignPermission(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        if (role.getPermissions().contains(permission)) {
            return "Permission already assigned to role";
        }

        role.getPermissions().add(permission);
        roleRepository.save(role);

        return "Permission assigned to role successfully";
    }

    @Override
    public String assignRoleToUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        userRepository.save(user);

        return "Role assigned to user successfully";
    }

    private RoleResponse map(Role role) {

        Set<String> permissionNames = role.getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return RoleResponse.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .status(role.getStatus())
                .permissions(permissionNames)
                .build();
    }

}