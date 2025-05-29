package utn.tacs.grupo5.telegrambot.dto.card;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CardInputDto {
    private UUID gameId;
    private String name;
    private String imageUrl;
    private String externalId;

}
