package com.uniquehire.rolemanagement.service;

import com.uniquehire.rolemanagement.dto.request.OrganizationRequest;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;

public interface OrganizationService {

    ApiResponse createOrganization(OrganizationRequest request);

    ApiResponse getOrganizationById(Long id);

    ApiResponse getAllOrganizations(int page, int size);

    ApiResponse updateOrganization(Long id, OrganizationRequest request);

    ApiResponse deleteOrganization(Long id);

}