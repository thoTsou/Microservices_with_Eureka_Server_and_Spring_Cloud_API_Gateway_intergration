package com.thotsou.user.register.controller;

import com.thotsou.user.register.model.AuthRequest;
import com.thotsou.user.register.model.LoginApiResponse;
import com.thotsou.user.register.model.RegisterApiResponse;
import com.thotsou.user.register.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterApiResponse> registerUser(@RequestBody AuthRequest authRequest) {
        return authService.registerUser(authRequest);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginApiResponse> provideUserWithJWTs(
            @RequestBody AuthRequest authRequest,
            @CookieValue(value = "REFRESH", required = false) String refreshJWT,
            HttpServletResponse servletResponse) {
        return authService.generateJWTsForUser(authRequest, refreshJWT, servletResponse);
    }

}
