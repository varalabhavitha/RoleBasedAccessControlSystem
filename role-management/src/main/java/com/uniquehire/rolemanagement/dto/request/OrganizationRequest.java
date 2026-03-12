package com.uniquehire.rolemanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRequest {

    private String organizationName;

    private String organizationCode;

    private String address;

    private Long createdBy;

    private Long updatedBy;

}