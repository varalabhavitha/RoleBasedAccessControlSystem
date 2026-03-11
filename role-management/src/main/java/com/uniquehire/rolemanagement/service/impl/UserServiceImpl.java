package com.uniquehire.rolemanagement.service.impl;

import com.uniquehire.rolemanagement.dto.request.UserRequestDTO;
import com.uniquehire.rolemanagement.dto.response.UserResponseDTO;
import com.uniquehire.rolemanagement.entity.User;
import com.uniquehire.rolemanagement.exception.UserAlreadyExistsException;
import com.uniquehire.rolemanagement.exception.UserNotFoundException;
import com.uniquehire.rolemanagement.repository.UserRepository;
import com.uniquehire.rolemanagement.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        if(userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistsException("Email already exists");

        if(userRepository.existsByUsername(request.getUsername()))
            throw new UserAlreadyExistsException("Username already exists");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }

    private UserResponseDTO mapToResponse(User user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }
}