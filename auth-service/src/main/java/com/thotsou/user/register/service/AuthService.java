package com.thotsou.user.register.service;

import com.thotsou.user.register.model.*;
import com.thotsou.user.register.repository.UserRepository;
import com.thotsou.user.register.security.PasswordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordStorageService passwordStorageService;

    public ResponseEntity<LoginApiResponse> generateJWTsForUser(AuthRequest authRequest) {
        List<User> userList = this.userRepository.findByEmail(authRequest.getEmail());
        
        try {
            if (userList.size() == 1 && passwordStorageService.decrypt(userList.get(0).getPassword()).equalsIgnoreCase(authRequest.getPassword())){
                String accessToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.ACCESS.name());
                String refreshToken = jwtService.generateJWT(authRequest.getEmail(), JWTTokenType.REFRESH.name());

                LoginApiResponse loginApiResponse = new LoginApiResponse(
                        HttpStatus.OK.value(),
                        "User registered with Success",
                        accessToken,
                        refreshToken
                );
                return ResponseEntity.ok(loginApiResponse);
            } else {
                LoginApiResponse loginApiResponse = new LoginApiResponse(HttpStatus.OK.value(), "Register first, then login", "", "");
                return ResponseEntity.ok(loginApiResponse);
            }
        } catch (Exception e) {
            LoginApiResponse loginApiResponse = new LoginApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Make sure that you provide a valid request body in json format",
                    "",
                    ""
            );
            return ResponseEntity.badRequest().body(loginApiResponse);
        }
    }

    public ResponseEntity<RegisterApiResponse> registerUser(AuthRequest authRequest) {
        if (this.userRepository.findByEmail(authRequest.getEmail()).isEmpty()) {
            try {
                User newApplicationUser = new User(authRequest.getEmail(), passwordStorageService.encrypt(authRequest.getPassword()));
                this.userRepository.save(newApplicationUser);

                RegisterApiResponse registerApiResponse = new RegisterApiResponse(
                        HttpStatus.OK.value(),
                        newApplicationUser.toString()
                );
                return ResponseEntity.ok(registerApiResponse);
            } catch (Exception e) {
                RegisterApiResponse registerApiResponse = new RegisterApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Make sure that you provide a valid request body in json format"
                );
                return ResponseEntity.badRequest().body(registerApiResponse);
            }
            
        } else {
            RegisterApiResponse registerApiResponse = new RegisterApiResponse(
                    HttpStatus.OK.value(),
                    "User with given email is already registered"
            );
            return ResponseEntity.ok(registerApiResponse);
        }
    }

}
