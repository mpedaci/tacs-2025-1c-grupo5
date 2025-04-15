package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.dto.game.GameOutputDto;

@Data
@NoArgsConstructor
@Schema(name = "Card", description = "Card schema for output")
public class CardOutputDto {

    private Long id;
    private GameOutputDto game;
    private String name;
    private String description;
    // private String imageUrl;

}
