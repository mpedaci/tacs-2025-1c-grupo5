package utn.tacs.grupo5.entity.offer;

import lombok.Data;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Offer {

    private Long id;
    private User user;
    private Post post;
    private List<OfferedCard> offeredCards;
    private Float money;
    private OfferState state;
    private LocalDateTime publishDate;
    private LocalDateTime finishDate;

}
