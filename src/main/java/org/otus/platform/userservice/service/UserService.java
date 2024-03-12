package org.otus.platform.userservice.service;

import org.otus.platform.userservice.dto.CreateUserRequest;
import org.otus.platform.userservice.dto.UpdateUserRequest;
import org.otus.platform.userservice.dto.UserDto;
import org.otus.platform.userservice.model.user.UserRole;

import java.util.UUID;

public interface UserService {
    UserDto createUser(CreateUserRequest userDto);
    UserDto getUserById(UUID id);
    UserDto getUserByEmail(String email);
    UserDto updateUser(UpdateUserRequest userDto);
    UserDto updateUserRole(UUID id, UserRole role);
}
