package utn.tacs.grupo5.telegrambot.dto.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GameOutputDto {

    private UUID id;
    private String title;
    private String description;

}
