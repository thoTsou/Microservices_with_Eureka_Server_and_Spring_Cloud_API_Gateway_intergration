package com.thotsou.user.register.service;

import com.thotsou.user.register.model.AuthRequest;
import com.thotsou.user.register.model.AuthResponse;
import com.thotsou.user.register.model.JWTTokenType;
import com.thotsou.user.register.model.User;
import com.thotsou.user.register.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public ResponseEntity<?> generateJWTsForUser(AuthRequest authRequest) {
        if (this.userRepository.findByEmail(authRequest.getEmail()).size() == 1){
            String accessToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.ACCESS.name());
            String refreshToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.REFRESH.name());
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } else {
            return ResponseEntity.ok("Register first, then login");
        }
    }

    public ResponseEntity<String> registerUser(AuthRequest authRequest) {
        if (this.userRepository.findByEmail(authRequest.getEmail()).isEmpty()) {
            User newApplicationUser = new User(authRequest.getEmail(), authRequest.getPassword());
            this.userRepository.save(newApplicationUser);
            return ResponseEntity.ok("Register user with success");
        } else {
            return ResponseEntity.ok("User with given email is already registered");
        }
    }

}
