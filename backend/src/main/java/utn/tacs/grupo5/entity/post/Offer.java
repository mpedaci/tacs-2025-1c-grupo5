package utn.tacs.grupo5.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import utn.tacs.grupo5.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "offers")
@Data
public class Offer {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonIgnore
    private UUID offererId;
    @Transient
    private User offerer;
    @JsonIgnore
    private UUID postId;
    @Transient
    private Post post;
    private List<OfferedCard> offeredCards;
    private BigDecimal money;
    private Status status;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime finishedAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
