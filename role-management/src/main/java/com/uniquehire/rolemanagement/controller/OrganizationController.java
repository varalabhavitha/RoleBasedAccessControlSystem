package com.uniquehire.rolemanagement.controller;

import com.uniquehire.rolemanagement.dto.request.OrganizationRequest;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.service.OrganizationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ApiResponse createOrganization(@RequestBody OrganizationRequest request) {
        return organizationService.createOrganization(request);
    }

    @GetMapping("/{id}")
    public ApiResponse getOrganization(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    @GetMapping
    public ApiResponse getAllOrganizations(
            @RequestParam int page,
            @RequestParam int size) {

        return organizationService.getAllOrganizations(page, size);
    }

    @PutMapping("/{id}")
    public ApiResponse updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationRequest request) {

        return organizationService.updateOrganization(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteOrganization(@PathVariable Long id) {
        return organizationService.deleteOrganization(id);
    }
}