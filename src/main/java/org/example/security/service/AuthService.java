package org.example.security.service;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.BusinessException;
import org.example.common.result.R;
import org.example.security.dto.LoginRequest;
import org.example.security.dto.LoginResponse;
import org.example.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public R<LoginResponse> login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            LoginResponse response = new LoginResponse(token, "Bearer", jwtUtil.getExpirationTime());
            return R.success(response);
        } catch (AuthenticationException e) {
            throw new BusinessException("登录失败", e);
        }
    }
} 