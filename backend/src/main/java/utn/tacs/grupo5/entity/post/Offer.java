package utn.tacs.grupo5.entity.post;

import lombok.Data;
import utn.tacs.grupo5.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Offer {

    private Long id;
    private User offerer;
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
