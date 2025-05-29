package utn.tacs.grupo5.telegrambot.dto.card;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.telegrambot.dto.game.GameOutputDto;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CardOutputDto {
    private UUID id;
    private GameOutputDto game;
    private String name;
    private String imageUrl;
    private String externalId;
}
