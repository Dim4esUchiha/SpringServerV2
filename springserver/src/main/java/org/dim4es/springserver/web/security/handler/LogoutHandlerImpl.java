package org.dim4es.springserver.web.security.handler;

import org.dim4es.springserver.model.Token;
import org.dim4es.springserver.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.dim4es.springserver.service.Constants.AUTH_HEADER;
import static org.dim4es.springserver.service.Constants.AUTH_HEADER_TOKEN_START;

@Component
public class LogoutHandlerImpl implements LogoutHandler {

    private final TokenService tokenService;

    @Autowired
    public LogoutHandlerImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeaderValue = request.getHeader(AUTH_HEADER);
        if (authHeaderValue != null && authHeaderValue.startsWith(AUTH_HEADER_TOKEN_START)) {

            String tokenValue = authHeaderValue.substring(AUTH_HEADER_TOKEN_START.length());

            Optional<Token> tokenOptional = tokenService.findByTokenValue(tokenValue);
            if (tokenOptional.isPresent()) {
                tokenService.makeInactive(tokenOptional.get());

                SecurityContextHolder.clearContext();
            }
        }
    }
}
