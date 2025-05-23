package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostInputDTO {
    private String userId;
    private List<String> images;
    private String cardId;
    private String conservationStatus;
    private BigDecimal estimatedValue;
    private List<String> wantedCardsIds;
    private String description;
}
