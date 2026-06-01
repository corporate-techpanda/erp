package com.techpanda.erp.auth.controller;

import com.techpanda.erp.auth.dto.LoginRequest;
import com.techpanda.erp.auth.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String result = authService.login(request);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        return new ResponseEntity<>(authentication.name(), HttpStatus.OK);
    }
}
