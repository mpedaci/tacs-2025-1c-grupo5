package utn.tacs.grupo5.telegrambot.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInputDto {

    private UUID userId;
    private List<String> images;
    private UUID cardId;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<UUID> wantedCardsIds;
    private String description;

}
