package utn.tacs.grupo5.telegrambot.dto.game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameInputDto {
    private String title;
    private String name;
    private String description;

}
