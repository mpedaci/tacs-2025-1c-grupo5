package utn.tacs.grupo5.telegrambot.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {
    private String name;
    private String username;
    private String password;
}
