package com.uniquehire.rolemanagement.controller;


import com.uniquehire.rolemanagement.dto.request.PermissionRequestDto;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.dto.response.PermissionResponseDto;
import com.uniquehire.rolemanagement.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @PostMapping
    public ApiResponse<PermissionResponseDto> create(
            @Valid @RequestBody PermissionRequestDto dto){

        return service.createPermission(dto);
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionResponseDto> getById(
            @PathVariable Long id){

        return service.getPermissionById(id);
    }

    @GetMapping
    public ApiResponse<List<PermissionResponseDto>> getPermissions(
            @RequestParam int page,
            @RequestParam int size){

        return service.getAllPermissions(page, size);
    }

    @PutMapping("/{id}")
    public ApiResponse<PermissionResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PermissionRequestDto dto){

        return service.updatePermission(id, dto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable Long id){

        return service.deletePermission(id);
    }
}