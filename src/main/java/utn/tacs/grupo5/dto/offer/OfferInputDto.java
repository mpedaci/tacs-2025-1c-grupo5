package utn.tacs.grupo5.dto.offer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utn.tacs.grupo5.dto.offeredCard.OfferedCardInputDto;

import java.util.List;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@ToString
@Schema(name = "Offer Input Schema", description = "Offer schema for input")
public class OfferInputDto {
    private Long offererId;
    private Long postId;
    private Float money;
    private List<OfferedCardInputDto> offeredCards;
}
