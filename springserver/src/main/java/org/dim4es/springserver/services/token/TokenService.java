package org.dim4es.springserver.services.token;

import org.dim4es.springserver.models.Token;
import org.dim4es.springserver.models.User;

import java.util.Optional;

public interface TokenService {

    Token saveNewToken(String token, User user);

    Optional<Token> findByTokenValue(String token);

    void makeInactive(Token token);

    void deactivateUserTokens(Long userId);
}
