package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;

public interface IAuthService {
    AuthOutputDto login(AuthInputDto authInputDto);

    AuthOutputDto refreshToken(String refreshToken);

    void logout(String refreshToken);
}
