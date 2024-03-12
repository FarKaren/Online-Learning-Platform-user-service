package org.otus.platform.userservice.dto;


import lombok.Builder;
import org.otus.platform.userservice.model.user.UserRole;

import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String name,
        String email,
        String phone,
        String password,
        UserRole role
) {
}

