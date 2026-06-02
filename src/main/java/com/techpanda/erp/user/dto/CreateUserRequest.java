package com.techpanda.erp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(

        @NotBlank
        String firstName,

        String lastName,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String mobileNumber,

        @NotNull
        Long branchId,

        @NotBlank
        String role,

        @NotBlank
        String password

) {
}
