package com.techpanda.erp.auth.dto;

public record LoginResponse(
        Long userId,
        String accessToken,
        String email,
        String role
) {
}
