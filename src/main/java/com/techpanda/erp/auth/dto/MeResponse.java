package com.techpanda.erp.auth.dto;

public record MeResponse(
        Long id,
        String email,
        String role,
        String branchCode,
        String branch
) {
}
