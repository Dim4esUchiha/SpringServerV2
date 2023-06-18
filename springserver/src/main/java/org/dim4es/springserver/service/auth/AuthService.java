package org.dim4es.springserver.service.auth;

import org.dim4es.springserver.dto.AuthUserDto;
import org.dim4es.springserver.dto.LoginRequestDto;
import org.dim4es.springserver.dto.RegisterRequestDto;
import org.dim4es.springserver.service.exception.UnprocessableEntityException;

public interface AuthService {

    AuthUserDto register(RegisterRequestDto registerRequest) throws UnprocessableEntityException;

    AuthUserDto login(LoginRequestDto loginRequest);
}
