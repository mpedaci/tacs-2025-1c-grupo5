package utn.tacs.grupo5.telegrambot.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferInputDto {

    private BigDecimal money;
    private UUID postId;
    private UUID offererId;
    private List<OfferedCardInputDto> offeredCards;

}
