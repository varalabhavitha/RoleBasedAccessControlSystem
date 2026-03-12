package com.uniquehire.rolemanagement.service.impl;

import com.uniquehire.rolemanagement.dto.request.OrganizationRequest;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.dto.response.OrganizationResponse;
import com.uniquehire.rolemanagement.entity.Organization;
import com.uniquehire.rolemanagement.enums.MessageEnum;
import com.uniquehire.rolemanagement.exception.ResourceNotFoundException;
import com.uniquehire.rolemanagement.repository.OrganizationRepository;
import com.uniquehire.rolemanagement.service.OrganizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public ApiResponse createOrganization(OrganizationRequest request) {

        log.info("Creating organization with code {}", request.getOrganizationCode());

        Organization organization = new Organization();

        organization.setOrganizationName(request.getOrganizationName());
        organization.setOrganizationCode(request.getOrganizationCode());
        organization.setAddress(request.getAddress());
        organization.setStatus("ACTIVE");
        organization.setCreatedBy(request.getCreatedBy());
        organization.setCreatedAt(LocalDateTime.now());

        Organization saved = organizationRepository.save(organization);

        OrganizationResponse response = new OrganizationResponse();

        response.setOrganizationId(saved.getOrganizationId());
        response.setOrganizationName(saved.getOrganizationName());
        response.setOrganizationCode(saved.getOrganizationCode());
        response.setAddress(saved.getAddress());
        response.setStatus(saved.getStatus());
        response.setCreatedAt(saved.getCreatedAt());

        return new ApiResponse(
                true,
                MessageEnum.ORGANIZATION_CREATED.getMessage(),
                response
        );
    }

    @Override
    public ApiResponse getOrganizationById(Long id) {

        log.info("Fetching organization with id {}", id);

        Organization organization = organizationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageEnum.ORGANIZATION_NOT_FOUND.getMessage()
                        ));

        OrganizationResponse response = new OrganizationResponse();

        response.setOrganizationId(organization.getOrganizationId());
        response.setOrganizationName(organization.getOrganizationName());
        response.setOrganizationCode(organization.getOrganizationCode());
        response.setAddress(organization.getAddress());
        response.setStatus(organization.getStatus());
        response.setCreatedAt(organization.getCreatedAt());

        return new ApiResponse(
                true,
                MessageEnum.ORGANIZATION_FETCHED.getMessage(),
                response
        );
    }

    @Override
    public ApiResponse getAllOrganizations(int page, int size) {

        log.info("Fetching organizations page {} size {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<Organization> organizationPage = organizationRepository.findAll(pageable);

        List<OrganizationResponse> responseList = new ArrayList<>();

        for (Organization org : organizationPage.getContent()) {

            OrganizationResponse response = new OrganizationResponse();

            response.setOrganizationId(org.getOrganizationId());
            response.setOrganizationName(org.getOrganizationName());
            response.setOrganizationCode(org.getOrganizationCode());
            response.setAddress(org.getAddress());
            response.setStatus(org.getStatus());
            response.setCreatedAt(org.getCreatedAt());

            responseList.add(response);
        }

        return new ApiResponse(
                true,
                MessageEnum.ORGANIZATIONS_FETCHED.getMessage(),
                responseList
        );
    }

    @Override
    public ApiResponse updateOrganization(Long id, OrganizationRequest request) {

        log.info("Updating organization with id {}", id);

        Organization organization = organizationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageEnum.ORGANIZATION_NOT_FOUND.getMessage()
                        ));

        organization.setOrganizationName(request.getOrganizationName());
        organization.setOrganizationCode(request.getOrganizationCode());
        organization.setAddress(request.getAddress());
        organization.setUpdatedBy(request.getUpdatedBy());
        organization.setUpdatedAt(LocalDateTime.now());

        organizationRepository.save(organization);

        return new ApiResponse(
                true,
                MessageEnum.ORGANIZATION_UPDATED.getMessage(),
                null
        );
    }

    @Override
    public ApiResponse deleteOrganization(Long id) {

        log.info("Deleting organization with id {}", id);

        Organization organization = organizationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageEnum.ORGANIZATION_NOT_FOUND.getMessage()
                        ));

        organizationRepository.delete(organization);

        return new ApiResponse(
                true,
                MessageEnum.ORGANIZATION_DELETED.getMessage(),
                null
        );
    }
}