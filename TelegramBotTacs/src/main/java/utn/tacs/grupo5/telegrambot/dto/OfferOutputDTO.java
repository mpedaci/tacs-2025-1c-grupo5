package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OfferOutputDTO {
    private String money;
    private String postId;
    private String offererId;
    private List<OfferedCardsOutputDTO> offeredCards;
}
