package org.dim4es.springserver.services.token;

import org.dim4es.springserver.models.Token;
import org.dim4es.springserver.models.User;
import org.dim4es.springserver.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token saveNewToken(String tokenString, User user) {
        Token token = new Token(tokenString, true, user);
        return tokenRepository.save(token);
    }

    @Override
    public Optional<Token> findByTokenValue(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void makeInactive(Token token) {
        token.setActive(false);
        tokenRepository.save(token);
    }

    @Transactional
    @Override
    public void deactivateUserTokens(Long userId) {
        List<Token> activeTokens = tokenRepository.findByUserIdAndIsActive(userId, true);
        for (Token token : activeTokens) {
            token.setActive(false);
            tokenRepository.save(token);
        }
    }
}
