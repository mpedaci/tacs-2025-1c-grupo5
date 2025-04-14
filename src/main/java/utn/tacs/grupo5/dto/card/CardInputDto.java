package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Card Input Schema", description = "Card schema")
public class CardInputDto extends CardBaseDto {

    private Long gameId;
    // private String externalId;

    public CardInputDto(CardBaseDto dto, Long gameId) {
        super(
                dto.getName(),
                dto.getDescription());

        this.gameId = gameId;
        // this.externalId = externalId;
    }

}
