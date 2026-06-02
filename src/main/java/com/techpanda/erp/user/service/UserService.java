package com.techpanda.erp.user.service;

import com.techpanda.erp.user.dto.CreateUserRequest;
import com.techpanda.erp.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getUsers();
}
