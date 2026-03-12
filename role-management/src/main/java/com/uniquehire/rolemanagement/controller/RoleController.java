package com.uniquehire.rolemanagement.controller;

import com.uniquehire.rolemanagement.dto.request.RoleRequest;
import com.uniquehire.rolemanagement.dto.response.RoleResponse;
import com.uniquehire.rolemanagement.service.RoleService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @RequestBody RoleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(request));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getRoleById(
            @PathVariable Long roleId) {

        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long roleId,
            @RequestBody RoleRequest request) {

        return ResponseEntity.ok(roleService.updateRole(roleId, request));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(
            @PathVariable Long roleId) {

        return ResponseEntity.ok(roleService.deleteRole(roleId));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<RoleResponse>> getRolesWithPagination(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(roleService.getRolesWithPagination(page, size));
    }
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<String> assignPermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {

        return ResponseEntity.ok(
                roleService.assignPermission(roleId, permissionId)
        );
    }
    @PostMapping("/{roleId}/users/{userId}")
    public ResponseEntity<String> assignRoleToUser(
            @PathVariable Long roleId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                roleService.assignRoleToUser(userId, roleId)
        );
    }
}