package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LogInRequestDTO {
    private String username;
    private String password;

    public LogInRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
