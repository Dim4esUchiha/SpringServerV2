package org.dim4es.springserver.service.auth;

import org.dim4es.springserver.dto.AuthUserDto;
import org.dim4es.springserver.dto.LoginRequestDto;
import org.dim4es.springserver.dto.RegisterRequestDto;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.service.exception.UnprocessableEntityException;
import org.dim4es.springserver.service.token.TokenService;
import org.dim4es.springserver.service.user.UserService;
import org.dim4es.springserver.web.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceImpl(UserService userService,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authManager,
                           TokenService tokenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public AuthUserDto register(RegisterRequestDto registerRequest) throws UnprocessableEntityException {
        Optional<User> existingUser = userService.findByUsername(registerRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new UnprocessableEntityException("User with that username already exists");
        }
        User user = new User(registerRequest.getEmail(),
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()));
        userService.addUser(user);

        String generatedToken = jwtService.generateFromUser(user);
        tokenService.saveNewToken(generatedToken, user);
        return new AuthUserDto(user, generatedToken);
    }

    @Override
    public AuthUserDto login(LoginRequestDto loginRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        User user = userService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user cannot be null"));

        tokenService.deactivateUserTokens(user.getId());
        String generatedToken = jwtService.generateFromUser(user);
        tokenService.saveNewToken(generatedToken, user);
        return new AuthUserDto(user, generatedToken);
    }
}
