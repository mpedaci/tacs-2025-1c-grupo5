package utn.tacs.grupo5.dto.offer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Schema(name = "Offer Input Schema", description = "Offer schema for input")
public class OfferInputDto extends OfferBaseDto {
    public Publication getPublication() {
    }

    public List<Card> getCards() {
    }

    public Float getMoney() {
    }

    public OfferState getState() {
    }
}
