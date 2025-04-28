package utn.tacs.grupo5.dto.offer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Offer Input", description = "Offer schema for input")
public class OfferInputDto {

    private BigDecimal money;
    private UUID postId;
    private UUID offererId;
    private List<OfferedCardInputDto> offeredCards;

}
