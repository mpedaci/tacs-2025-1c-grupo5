package utn.tacs.grupo5.entity;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class Offer {

    private Long id;
    private User offerUser;
    private Publication publication;
    private List<OfferedCard> offeredCards;
    private Float money;
    private OfferState state;
    private LocalDateTime publicationDate;
    private LocalDateTime offerEndDate;

}


