package utn.tacs.grupo5.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import utn.tacs.grupo5.entity.card.Card;

import java.util.UUID;

@Data
public class OfferedCard {
    @JsonIgnore
    private UUID cardId;
    @Transient
    private Card card;
    private ConservationStatus conservationStatus;
    private String imageUrl;
}