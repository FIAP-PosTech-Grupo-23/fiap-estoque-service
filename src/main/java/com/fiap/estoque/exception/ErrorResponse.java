package com.fiap.estoque.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) {}
