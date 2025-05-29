package utn.tacs.grupo5.telegrambot.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthInputDto {
    private String username;
    private String password;
}
