package com.techpanda.erp.user.dto;

public record UserResponse(

        Long id,
        String firstName,
        String email,
        String role,
        String branch,
        boolean enabled

) {
}