package org.otus.platform.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.otus.platform.userservice.dto.CreateUserRequest;
import org.otus.platform.userservice.dto.UpdateUserRequest;
import org.otus.platform.userservice.dto.UserDto;
import org.otus.platform.userservice.model.user.UserRole;
import org.otus.platform.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@NotNull @PathVariable UUID id) {
        var response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email")
    ResponseEntity<UserDto> getUserByEmail(@NotNull @RequestParam String email) {
        var response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest dto) {
        var response = userService.createUser(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserRequest dto) {
        var response = userService.updateUser(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/role")
    ResponseEntity<UserDto> updateUserRole(
            @NotNull @RequestParam("id") UUID id,
            @NotNull @RequestParam("role") UserRole role
    ) {
        var response = userService.updateUserRole(id, role);
        return ResponseEntity.ok(response);
    }
}
