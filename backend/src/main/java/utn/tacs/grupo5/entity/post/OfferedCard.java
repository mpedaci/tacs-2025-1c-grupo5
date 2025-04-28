package utn.tacs.grupo5.entity.post;

import lombok.Data;
import utn.tacs.grupo5.entity.card.Card;

@Data
public class OfferedCard {

    private Card card;
    private ConservationStatus conservationStatus;
    private String imageUrl;

}
