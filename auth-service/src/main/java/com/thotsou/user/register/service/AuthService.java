package com.thotsou.user.register.service;

import com.thotsou.user.register.model.AuthRequest;
import com.thotsou.user.register.model.AuthResponse;
import com.thotsou.user.register.model.JWTTokenType;
import com.thotsou.user.register.model.User;
import com.thotsou.user.register.repository.UserRepository;
import com.thotsou.user.register.security.PasswordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordStorageService passwordStorageService;

    public ResponseEntity<?> generateJWTsForUser(AuthRequest authRequest) {
        List<User> userList = this.userRepository.findByEmail(authRequest.getEmail());
        
        try {
            if (userList.size() == 1 && passwordStorageService.decrypt(userList.get(0).getPassword()).equalsIgnoreCase(authRequest.getPassword())){
                String accessToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.ACCESS.name());
                String refreshToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.REFRESH.name());
                return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
            } else {
                return ResponseEntity.ok("Register first, then login");
            }
        } catch (Exception e) {
            // should do something better....
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<String> registerUser(AuthRequest authRequest) {
        if (this.userRepository.findByEmail(authRequest.getEmail()).isEmpty()) {
            try {
                User newApplicationUser = new User(authRequest.getEmail(), passwordStorageService.encrypt(authRequest.getPassword()));
                this.userRepository.save(newApplicationUser);
                return ResponseEntity.ok("Register user with success");
            } catch (Exception e) {
                // should do something better....
                return ResponseEntity.internalServerError().build();
            }
            
        } else {
            return ResponseEntity.ok("User with given email is already registered");
        }
    }

}
