package com.uniquehire.rolemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrganizationResponse {

    private Long organizationId;

    private String organizationName;

    private String organizationCode;

    private String address;

    private String status;

    private LocalDateTime createdAt;

}