package org.otus.platform.userservice.exception.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
