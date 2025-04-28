package utn.tacs.grupo5.entity.card;

import lombok.Data;
import java.util.UUID;

@Data
public class Card {
    private UUID id;
    private Game game;
    private String name;
    private String imageUrl;
    private String externalId;
}
