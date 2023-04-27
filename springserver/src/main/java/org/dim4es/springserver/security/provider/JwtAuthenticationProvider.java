package org.dim4es.springserver.security.provider;

import org.dim4es.springserver.security.JwtAuthentication;
import org.dim4es.springserver.security.JwtService;
import org.dim4es.springserver.security.exception.InvalidTokenException;
import org.dim4es.springserver.security.filter.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationProvider(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String subject;
        try {
            subject = jwtService.extractSubject(token);
        } catch (RuntimeException e) {
            throw new InvalidTokenException("Token is invalid", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

        JwtAuthentication auth = new JwtAuthentication(subject, token, userDetails);
        auth.setAuthenticated(true);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationFilter.JwtAuthenticationRequest.class.isAssignableFrom(authentication);
    }
}
