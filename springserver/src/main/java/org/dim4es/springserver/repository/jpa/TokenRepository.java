package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByUserIdAndIsActive(Long userId, boolean active);
}
