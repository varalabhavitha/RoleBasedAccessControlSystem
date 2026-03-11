package com.uniquehire.rolemanagement.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String username;

    private String email;

    private Boolean status;

    private LocalDateTime createdAt;
}