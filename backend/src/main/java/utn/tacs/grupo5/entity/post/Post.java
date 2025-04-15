package utn.tacs.grupo5.entity.post;

import lombok.Data;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {

    private Long id;
    private User user;
    private List<String> images;
    private Card card;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<Card> wantedCards;
    private Status status;
    private String description;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime finishedAt;

    public enum Status {
        PUBLISHED,
        FINISHED,
        CANCELLED
    }
}
