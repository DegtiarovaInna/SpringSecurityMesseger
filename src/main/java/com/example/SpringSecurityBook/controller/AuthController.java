package com.example.SpringSecurityBook.controller;
import com.example.SpringSecurityBook.dto.*;
import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.service.AppUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringSecurityBook.service.AuthService;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

//Обрабатывает HTTP-запросы от клиента, вызывает соответствующие методы в UserService и возвращает ответы.
// пока есть  регистрация, вход и обновление токенов.
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
  //  private final JwtSecurityService jwtSecurityService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(refreshTokenRequestDto));
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
//        UserDetails userDetails = appUserService.getDetailsService().loadUserByUsername(email);
//        if (userDetails != null) {
//            // Validate password (implement password checking logic as needed)
//            String token = jwtSecurityService.generateToken(userDetails);
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(401).body("Invalid credentials");
//    }
//
//    @GetMapping("/validate")
//    public ResponseEntity<String> validateToken(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        if (token != null && jwtSecurityService.validateToken(token, appUserService.getDetailsService().loadUserByUsername(jwtSecurityService.extractUsername(token)))) {
//            return ResponseEntity.ok("Token is valid");
//        }
//        return ResponseEntity.status(401).body("Token is invalid or expired");
//    }
}
