package org.dim4es.springserver.web.security.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.dim4es.springserver.service.Constants.AUTH_HEADER;
import static org.dim4es.springserver.service.Constants.AUTH_HEADER_TOKEN_START;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(AUTH_HEADER);
        if (authHeaderValue != null && authHeaderValue.startsWith(AUTH_HEADER_TOKEN_START)) {

            String tokenValue = authHeaderValue.substring(AUTH_HEADER_TOKEN_START.length());
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationRequest(tokenValue));
        }
        filterChain.doFilter(request, response);
    }

    public static class JwtAuthenticationRequest extends AbstractAuthenticationToken {

        private final String token;

        public JwtAuthenticationRequest(String token) {
            super(null);
            this.token = token;
        }

        @Override
        public Object getCredentials() {
            return token;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }
    }
}
