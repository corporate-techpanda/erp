package com.techpanda.erp.auth.service;

import com.techpanda.erp.auth.dto.LoginRequest;
import com.techpanda.erp.auth.dto.LoginResponse;
import com.techpanda.erp.common.exception.UnauthorizedAccess;
import com.techpanda.erp.security.jwt.JwtService;
import com.techpanda.erp.security.model.CustomUserDetails;
import com.techpanda.erp.user.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public LoginResponse login(LoginRequest request) {
    try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            User user = userDetails.getUser();
            String token = jwtService.generateToken(userDetails);

            return new LoginResponse(
                    user.getId(),
                    token,
                    userDetails.getUsername(), // email
                    userDetails.getAuthorities().toString()
            );
        } catch (BadCredentialsException ex) {

        throw new UnauthorizedAccess(
                "Invalid email or password"
        );

    } catch (DisabledException ex) {

        throw new UnauthorizedAccess(
                "User account is disabled"
        );

    } catch (LockedException ex) {

        throw new UnauthorizedAccess(
                "User account is locked"
        );
    }
    }
}
