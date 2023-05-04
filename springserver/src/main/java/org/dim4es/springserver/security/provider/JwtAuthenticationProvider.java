package org.dim4es.springserver.security.provider;

import org.dim4es.springserver.models.Token;
import org.dim4es.springserver.security.exception.InvalidTokenException;
import org.dim4es.springserver.security.filter.JwtAuthenticationFilter;
import org.dim4es.springserver.security.jwt.JwtAuthentication;
import org.dim4es.springserver.security.jwt.JwtService;
import org.dim4es.springserver.services.token.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    public JwtAuthenticationProvider(JwtService jwtService,
                                     UserDetailsService userDetailsService,
                                     TokenService tokenService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String tokenString = (String) authentication.getCredentials();
        String subject;
        try {
            subject = jwtService.extractSubject(tokenString);
        } catch (RuntimeException e) {
            throw new InvalidTokenException("Token is invalid", e);
        }
        Optional<Token> tokenOptional = tokenService.findByTokenValue(tokenString);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            if (!token.isActive()) {
                throw new InvalidTokenException("Token is not active");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            JwtAuthentication auth = new JwtAuthentication(subject, tokenString, userDetails);
            auth.setAuthenticated(true);
            return auth;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationFilter.JwtAuthenticationRequest.class.isAssignableFrom(authentication);
    }
}
