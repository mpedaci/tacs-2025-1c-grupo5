package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OfferedCardsOutputDTO {
    private String cardId;
    private String conservationStatus;
    private String image = null;
}
