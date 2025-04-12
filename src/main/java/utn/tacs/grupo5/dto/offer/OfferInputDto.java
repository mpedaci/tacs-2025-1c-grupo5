package utn.tacs.grupo5.dto.offer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utn.tacs.grupo5.entity.OfferedCard;
import utn.tacs.grupo5.entity.OfferState;
import utn.tacs.grupo5.entity.Publication;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Schema(name = "Offer Input Schema", description = "Offer schema for input")
public class OfferInputDto extends OfferBaseDto {
    public Publication getPublication() {
        return null;
    }

    public List<OfferedCard> getCards() {
        return List.of();
    }

    public Float getMoney() {
        return 0f;
    }

    public OfferState getState() {
        return null;
    }
}
