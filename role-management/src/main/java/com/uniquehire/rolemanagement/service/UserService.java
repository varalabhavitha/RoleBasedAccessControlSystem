package com.uniquehire.rolemanagement.service;

import com.uniquehire.rolemanagement.dto.request.UserRequestDTO;
import com.uniquehire.rolemanagement.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    void deleteUser(Long id);
}