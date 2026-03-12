package com.uniquehire.rolemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
@Getter
@Setter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String organizationName;

    private String organizationCode;

    private String address;

    private String status;

    private LocalDateTime createdAt;

    private Long createdBy;

    private Long updatedBy;

    private LocalDateTime updatedAt;

}