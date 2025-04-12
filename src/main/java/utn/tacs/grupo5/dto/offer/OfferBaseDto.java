package utn.tacs.grupo5.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.entity.OfferState;
import utn.tacs.grupo5.entity.OfferedCard;
import utn.tacs.grupo5.entity.Publication;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class OfferBaseDto {

    private Publication publication;
    private List<OfferedCard> offeredCards;
    private Float money;
    private OfferState state;
    private LocalDateTime publicationDate;
    private LocalDateTime finished;
}
