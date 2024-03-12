package org.otus.platform.userservice.dto.error;



import org.springframework.http.HttpStatus;

import java.util.List;

public record ArgumentNotValidDto(HttpStatus code, String message, List<String> errors) {
}
