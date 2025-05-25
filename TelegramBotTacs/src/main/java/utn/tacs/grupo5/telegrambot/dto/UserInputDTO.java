package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInputDTO {
    private String id;
    private String name;
    private String username;
    private String createdAt;
    private String updatedAt;
}
