package utn.tacs.grupo5.service;

import org.springframework.security.core.Authentication;

import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;

public interface IAuthService {

    Authentication getAuthentication(String token);

    Boolean validateToken(String token);

    AuthOutputDto login(AuthInputDto authInputDto);

    AuthOutputDto refreshToken(String refreshToken);

    void logout(String refreshToken);
}
