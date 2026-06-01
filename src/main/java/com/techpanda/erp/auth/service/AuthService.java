package com.techpanda.erp.auth.service;

import com.techpanda.erp.auth.dto.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
}
