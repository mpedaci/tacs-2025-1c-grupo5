package utn.tacs.grupo5.dto.offer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utn.tacs.grupo5.entity.Publication;
import utn.tacs.grupo5.entity.OfferedCard;
import utn.tacs.grupo5.entity.OfferState;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(name = "Offer Output Schema", description = "Offer schema for output")
public class OfferOutputDto extends OfferBaseDto{
    public void setId(Long id) {
    }

    public void setPublication(Publication publication) {
    }

    public void setCards(List<OfferedCard> offeredCards) {
    }

    public void setMoney(Float money) {
    }

    public void setState(OfferState state) {
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
    }

    public void setFinished(LocalDateTime finished) {

    }

}
