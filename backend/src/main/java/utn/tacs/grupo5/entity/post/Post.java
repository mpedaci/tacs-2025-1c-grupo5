package utn.tacs.grupo5.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "posts")
@Data
public class Post {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonIgnore
    private UUID userId;
    @Transient
    private User user;
    private List<String> images;
    @JsonIgnore
    private UUID cardId;
    @Transient
    private Card card;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    @JsonIgnore
    private List<UUID> wantedCardsIds;
    @Transient
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
