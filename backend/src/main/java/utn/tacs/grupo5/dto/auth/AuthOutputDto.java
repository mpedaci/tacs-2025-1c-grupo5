package utn.tacs.grupo5.dto.auth;

import lombok.Getter;

@Getter
public class AuthOutputDto {
    private String token;

    public AuthOutputDto(String token) {
        this.token = token;
    }

}