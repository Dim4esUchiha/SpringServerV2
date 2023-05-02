package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.AuthUserDto;
import org.dim4es.springserver.dto.LoginRequestDto;
import org.dim4es.springserver.dto.RegisterRequestDto;
import org.dim4es.springserver.services.auth.AuthService;
import org.dim4es.springserver.services.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthUserDto> register(@RequestBody RegisterRequestDto requestDto)
            throws UnprocessableEntityException {
        return ResponseEntity.ok(authService.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
