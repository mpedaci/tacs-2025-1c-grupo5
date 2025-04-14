package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utn.tacs.grupo5.dto.game.GameOutputDto;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Card", description = "Card schema for output")
public class CardOutputDto extends CardBaseDto {

    private Long id;
    private GameOutputDto game;

    public CardOutputDto(CardBaseDto dto, Long id, GameOutputDto game) {
        super(
                dto.getName(),
                dto.getDescription());
        this.id = id;
        this.game = game;
    }

}
