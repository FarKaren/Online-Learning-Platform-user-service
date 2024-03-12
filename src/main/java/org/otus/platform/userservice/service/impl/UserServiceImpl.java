package org.otus.platform.userservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.platform.userservice.dto.CreateUserRequest;
import org.otus.platform.userservice.dto.UpdateUserRequest;
import org.otus.platform.userservice.dto.UserDto;
import org.otus.platform.userservice.exception.exceptions.EmailAlreadyExistException;
import org.otus.platform.userservice.mapper.UserMapper;
import org.otus.platform.userservice.model.user.UserRole;
import org.otus.platform.userservice.repository.UserRepository;
import org.otus.platform.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserRequest userDto) {
        log.info("invoke createUser() method");
        if (userRepository.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistException("User with email: " + userDto.email() + " already exist");
        }

        var newUser = userMapper.toEntity(userDto, UserRole.ROLE_STUDENT);
        var savedUser = userRepository.save(newUser);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(UUID id) {
        log.info("invoke getUserById() method");
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("invoke getUserByEmail() method");
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found"));

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest dto) {
        log.info("invoke updateUser() method");
        var user = userRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + dto.id() + " not found"));

        if (!dto.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.email())) {
                throw new EmailAlreadyExistException("User with email: " + dto.email() + " already exist");
            }
        }

        user.setName(dto.name());
        user.setPhone(dto.phone());
        user.setEmail(dto.email());
        userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUserRole(UUID id, UserRole role) {
        log.info("invoke updateUserRole() method");
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));

        user.setRole(role);
        userRepository.save(user);

        return userMapper.toUserDto(user);
    }
}
