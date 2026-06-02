package com.techpanda.erp.security.util;

import com.techpanda.erp.security.model.CustomUserDetails;
import com.techpanda.erp.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public CustomUserDetails getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

       return (CustomUserDetails) authentication.getPrincipal();
    }
}
