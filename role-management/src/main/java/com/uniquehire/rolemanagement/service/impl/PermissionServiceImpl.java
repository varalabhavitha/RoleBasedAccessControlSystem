package com.uniquehire.rolemanagement.service.impl;


import com.uniquehire.rolemanagement.dto.request.PermissionRequestDto;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.dto.response.PermissionResponseDto;
import com.uniquehire.rolemanagement.entity.Permission;
import com.uniquehire.rolemanagement.repository.PermissionRepository;
import com.uniquehire.rolemanagement.repository.UserRepository;
import com.uniquehire.rolemanagement.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository repository;


    @Override
    public ApiResponse<PermissionResponseDto> createPermission(PermissionRequestDto dto) {

        logger.info("Creating new permission with name: {}", dto.getName());

        try {

            Permission permission = new Permission();
            permission.setName(dto.getName());
            permission.setDescription(dto.getDescription());


            Permission saved = repository.save(permission);

            logger.info("Permission created successfully with ID: {}", saved.getId());

            return new ApiResponse<>(
                    true,
                    "Permission created successfully",
                    mapToResponse(saved)
            );

        } catch (Exception e) {

            logger.error("Error while creating permission", e);

            return new ApiResponse<>(
                    false,
                    "Error creating permission",
                    null
            );
        }
    }

    @Override
    public ApiResponse<PermissionResponseDto> getPermissionById(Long id) {

        logger.info("Fetching permission with ID: {}", id);

        Permission permission = repository.findById(id).orElse(null);

        if (permission == null) {

            logger.warn("Permission not found with ID: {}", id);

            return new ApiResponse<>(
                    false,
                    "Permission not found",
                    null
            );
        }

        logger.info("Permission fetched successfully for ID: {}", id);

        return new ApiResponse<>(
                true,
                "Permission fetched successfully",
                mapToResponse(permission)
        );
    }

    @Override
    public ApiResponse<List<PermissionResponseDto>> getAllPermissions(int page, int size) {

        logger.info("Fetching permissions with page: {} and size: {}", page, size);

        try {

            Page<Permission> permissions = repository.findAll(PageRequest.of(page, size));

            List<PermissionResponseDto> responseList = permissions
                    .map(this::mapToResponse)
                    .getContent();

            logger.info("Fetched {} permissions", responseList.size());

            return new ApiResponse<>(
                    true,
                    "Permissions fetched successfully",
                    responseList
            );

        } catch (Exception e) {

            logger.error("Error fetching permissions", e);

            return new ApiResponse<>(
                    false,
                    "Error fetching permissions",
                    null
            );
        }

    }

    @Override
    public ApiResponse<PermissionResponseDto> updatePermission(Long id, PermissionRequestDto dto) {

        logger.info("Updating permission with ID: {}", id);

        Permission permission = repository.findById(id).orElse(null);

        if (permission == null) {

            logger.warn("Permission not found for update with ID: {}", id);

            return new ApiResponse<>(
                    false,
                    "Permission not found",
                    null
            );
        }

        permission.setName(dto.getName());
        permission.setDescription(dto.getDescription());

        Permission updated = repository.save(permission);

        logger.info("Permission updated successfully for ID: {}", id);

        return new ApiResponse<>(
                true,
                "Permission updated successfully",
                mapToResponse(updated)
        );
    }

    @Override
    public ApiResponse<String> deletePermission(Long id) {

        logger.info("Deleting permission with ID: {}", id);

        Permission permission = repository.findById(id).orElse(null);

        if (permission == null) {

            logger.warn("Permission not found for deletion with ID: {}", id);

            return new ApiResponse<>(
                    false,
                    "Permission not found",
                    null
            );
        }

        repository.delete(permission);

        logger.info("Permission deleted successfully with ID: {}", id);

        return new ApiResponse<>(
                true,
                "Permission deleted successfully",
                null
        );
    }

    private PermissionResponseDto mapToResponse(Permission permission) {

        PermissionResponseDto dto = new PermissionResponseDto();

        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());

        return dto;
    }
}