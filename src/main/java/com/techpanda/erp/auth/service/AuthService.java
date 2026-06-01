package com.techpanda.erp.auth.service;

import com.techpanda.erp.auth.dto.LoginRequest;
import com.techpanda.erp.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
