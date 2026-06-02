package com.techpanda.erp.user.controller;

import com.techpanda.erp.user.dto.CreateUserRequest;
import com.techpanda.erp.user.dto.UserResponse;
import com.techpanda.erp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest createUserRequest
        )
    {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserResponse>> getUser(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
