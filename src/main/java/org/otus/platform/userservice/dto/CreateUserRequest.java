package org.otus.platform.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(
        @NotNull(message = "Name must not be null")
        String name,

        @NotNull(message = "Email must be not null")
        @Email
        String email,

        @NotNull(message = "Phone must be not null")
        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
        String phone,

        @NotNull(message = "Password must be not null")
        String password
) {
}
