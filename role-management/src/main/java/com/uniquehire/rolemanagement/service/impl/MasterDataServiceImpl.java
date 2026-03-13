package com.uniquehire.rolemanagement.service.impl;
import com.uniquehire.rolemanagement.dto.response.*;
import com.uniquehire.rolemanagement.entity.*;
import com.uniquehire.rolemanagement.repository.*;
import com.uniquehire.rolemanagement.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public ApiResponse<MasterDataResponseDto> getMasterData() {

        List<OrganizationResponseDto> organizations = organizationRepository.findAll()
                .stream()
                .map(this::mapToOrganizationDto)
                .collect(Collectors.toList());

        List<DepartmentResponseDto> departments = departmentRepository.findAll()
                .stream()
                .map(this::mapToDepartmentDto)
                .collect(Collectors.toList());

        List<RoleResponse> roles = roleRepository.findAll()
                .stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList());

        List<PermissionResponseDto> permissions = permissionRepository.findAll()
                .stream()
                .map(this::mapToPermissionDto)
                .collect(Collectors.toList());

        List<TrainingResponseDto> trainings = trainingRepository.findAll()
                .stream()
                .map(this::mapToTrainingDto)
                .collect(Collectors.toList());

        MasterDataResponseDto responseDto = MasterDataResponseDto.builder()
                .organizations(organizations)
                .departments(departments)
                .roles(roles)
                .permissions(permissions)
                .trainings(trainings)
                .build();

        return ApiResponse.<MasterDataResponseDto>builder()
                .success(true)
                .message("Master data fetched successfully")
                .data(responseDto)
                .build();
    }

    private OrganizationResponseDto mapToOrganizationDto(Organization organization) {
        return OrganizationResponseDto.builder()
                .orgId(organization.getOrgId())
                .orgName(organization.getOrgName())
                .address(organization.getAddress())
                .build();
    }

    private DepartmentResponseDto mapToDepartmentDto(Department department) {
        return DepartmentResponseDto.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .numberOfTrainingsGoingOn(department.getNumberOfTrainingsGoingOn())
                .build();
    }

    private RoleResponse mapToRoleDto(Role role) {
        return RoleResponse.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .build();
    }

    private PermissionResponseDto mapToPermissionDto(Permission permission) {
        return PermissionResponseDto.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }

    private TrainingResponseDto mapToTrainingDto(Training training) {
        return TrainingResponseDto.builder()
                .trainingId(training.getTrainingId())
                .trainingName(training.getTrainingName())
                .build();
    }
}