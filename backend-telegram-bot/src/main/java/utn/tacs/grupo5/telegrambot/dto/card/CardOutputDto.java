package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.dto.game.GameOutputDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "Card", description = "Card schema for output")
public class CardOutputDto {
    private UUID id;
    private GameOutputDto game;
    private String name;
    private String imageUrl;
    private String externalId;
}
