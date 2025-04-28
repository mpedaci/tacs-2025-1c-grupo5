package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "Card Input", description = "Card schema for input")
public class CardInputDto {
    private UUID gameId;
    private String name;
    private String imageUrl;
    private String externalId;

}
