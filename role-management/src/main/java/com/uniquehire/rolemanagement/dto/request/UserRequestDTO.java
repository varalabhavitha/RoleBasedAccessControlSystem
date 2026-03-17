package com.uniquehire.rolemanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private Long roleId;
    private Long organizationId;
}