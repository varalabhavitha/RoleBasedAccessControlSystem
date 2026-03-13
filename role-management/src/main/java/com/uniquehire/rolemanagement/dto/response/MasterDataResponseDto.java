package com.uniquehire.rolemanagement.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterDataResponseDto {

    private List<OrganizationResponseDto> organizations;
    private List<DepartmentResponseDto> departments;
    private List<RoleResponse> roles;
    private List<PermissionResponseDto> permissions;
    private List<TrainingResponseDto> trainings;
}