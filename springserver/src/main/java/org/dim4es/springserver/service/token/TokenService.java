package org.dim4es.springserver.service.token;

import org.dim4es.springserver.model.Token;
import org.dim4es.springserver.model.User;

import java.util.Optional;

public interface TokenService {

    Token saveNewToken(String token, User user);

    Optional<Token> findByTokenValue(String token);

    void makeInactive(Token token);

    void deactivateUserTokens(Long userId);
}
