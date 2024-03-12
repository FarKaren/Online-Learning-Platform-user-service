package org.otus.platform.userservice.mapper;

import org.otus.platform.userservice.dto.CreateUserRequest;
import org.otus.platform.userservice.dto.UserDto;
import org.otus.platform.userservice.model.user.User;
import org.otus.platform.userservice.model.user.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User toEntity(CreateUserRequest dto, UserRole role) {
        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .password(dto.password())
                .role(role)
                .build();
    }
}
