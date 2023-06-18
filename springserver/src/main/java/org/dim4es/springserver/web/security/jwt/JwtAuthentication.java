package org.dim4es.springserver.web.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {

    private final String username;
    private final String token;
    private final UserDetails userDetails;
    private boolean isAuthenticated;
    private final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));

    public JwtAuthentication(String username, String token, UserDetails userDetails) {
        this.username = username;
        this.token = token;
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }
}
