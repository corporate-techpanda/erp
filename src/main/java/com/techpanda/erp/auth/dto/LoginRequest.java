package com.techpanda.erp.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
