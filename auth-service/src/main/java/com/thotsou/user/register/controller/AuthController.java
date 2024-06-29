package com.thotsou.user.register.controller;

import com.thotsou.user.register.model.AuthRequest;
import com.thotsou.user.register.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> provideUserWithJWTs(@RequestBody AuthRequest authRequest) {
        return authService.generateJWTsForUser(authRequest);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
        return authService.registerUser(authRequest);
    }

}
