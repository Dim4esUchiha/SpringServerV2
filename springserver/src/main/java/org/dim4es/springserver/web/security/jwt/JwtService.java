package org.dim4es.springserver.web.security.jwt;

import org.dim4es.springserver.model.User;

public interface JwtService {

    String generateFromUser(User user);

    String extractSubject(String token);
}
