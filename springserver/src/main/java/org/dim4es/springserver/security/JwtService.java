package org.dim4es.springserver.security;

import org.dim4es.springserver.models.User;

public interface JwtService {

    String generateFromUser(User user);

    String extractSubject(String token);
}
