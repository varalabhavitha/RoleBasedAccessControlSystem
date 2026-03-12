package com.uniquehire.rolemanagement.enums;

public enum MessageEnum {

    // ROLE
    ROLE_CREATED("Role created successfully"),
    ROLE_UPDATED("Role updated successfully"),
    ROLE_FETCHED("Role fetched successfully"),
    ROLES_FETCHED("Roles fetched successfully"),
    ROLE_DELETED("Role deleted successfully"),
    ROLE_NOT_FOUND("Role not found"),

    // ORGANIZATION
    ORGANIZATION_CREATED("Organization created successfully"),
    ORGANIZATION_UPDATED("Organization updated successfully"),
    ORGANIZATION_FETCHED("Organization fetched successfully"),
    ORGANIZATIONS_FETCHED("Organizations fetched successfully"),
    ORGANIZATION_DELETED("Organization deleted successfully"),
    ORGANIZATION_NOT_FOUND("Organization not found");

    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}