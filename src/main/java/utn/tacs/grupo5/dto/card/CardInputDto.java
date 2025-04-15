package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Card Input", description = "Card schema for input")
public class CardInputDto {

    private Long gameId;
    private String name;
    private String description;
    // private String imageUrl;
    // private String externalId;

}
