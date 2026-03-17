package com.uniquehire.rolemanagement.controller;
import com.uniquehire.rolemanagement.dto.request.OrganizationRequestDto;
import com.uniquehire.rolemanagement.dto.response.OrganizationResponseDto;
import com.uniquehire.rolemanagement.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public OrganizationResponseDto createOrganization(@Valid @RequestBody OrganizationRequestDto dto) {
        return organizationService.createOrganization(dto);
    }

    @GetMapping("/{id}")
    public OrganizationResponseDto getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    @GetMapping
    public List<OrganizationResponseDto> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PutMapping("/{id}")
    public OrganizationResponseDto updateOrganization(@PathVariable Long id,
                                                      @Valid @RequestBody OrganizationRequestDto dto) {
        return organizationService.updateOrganization(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return "Organization deleted successfully";
    }
}