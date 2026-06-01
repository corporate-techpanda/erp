package com.techpanda.erp.auth.controller;

import com.techpanda.erp.auth.dto.LoginRequest;
import com.techpanda.erp.auth.dto.LoginResponse;
import com.techpanda.erp.auth.dto.MeResponse;
import com.techpanda.erp.auth.service.AuthService;
import com.techpanda.erp.security.model.CustomUserDetails;
import com.techpanda.erp.security.service.CustomUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        MeResponse meResponse = new MeResponse(
                customUserDetails.getUser().getId(),
                customUserDetails.getUser().getEmail(),
                customUserDetails.getUser().getRole().getName(),
                customUserDetails.getUser().getBranch().getBranchCode(),
                customUserDetails.getUser().getBranch().getName()
        );
        return new ResponseEntity<>(meResponse, HttpStatus.OK);
    }
}
