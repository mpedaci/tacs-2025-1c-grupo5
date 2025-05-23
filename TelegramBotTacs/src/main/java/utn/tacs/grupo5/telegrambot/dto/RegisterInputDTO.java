package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInputDTO {
    private String name;
    private String username;
    private String password;
    private boolean admin;

    public RegisterInputDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        admin = false;
    }
}
