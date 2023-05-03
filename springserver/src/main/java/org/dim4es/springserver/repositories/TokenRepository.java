package org.dim4es.springserver.repositories;

import org.dim4es.springserver.models.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByUserIdAndIsActive(Long userId, boolean active);
}
