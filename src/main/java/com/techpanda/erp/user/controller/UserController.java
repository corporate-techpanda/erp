package com.techpanda.erp.user.controller;

import com.techpanda.erp.user.dto.CreateUserRequest;
import com.techpanda.erp.user.dto.UpdateUserStatusRequest;
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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<List<UserResponse>> getUser(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable Long id,
                                                     @RequestBody UpdateUserStatusRequest  updateUserStatusRequest
    ){
        return ResponseEntity.ok(
                userService.updateUserStatus(id,updateUserStatusRequest)
        );
    }
}
