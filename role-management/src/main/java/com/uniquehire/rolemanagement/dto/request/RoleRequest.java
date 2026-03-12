package com.uniquehire.rolemanagement.dto.request;

import com.uniquehire.rolemanagement.enums.Status;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    private String roleName;

    private String description;

    private Status status;

    private String createdBy;

    private String updatedBy;

    private Set<Long> permissionIds;
}