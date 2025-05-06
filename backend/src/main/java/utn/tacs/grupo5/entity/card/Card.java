package utn.tacs.grupo5.entity.card;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Document(collection = "cards")
@Data
public class Card {
//    Seguimos usando UUID o lo pasamod a ObjectId
    @Id
    private UUID id = UUID.randomUUID();
    private Game game;
    private String name;
    private String imageUrl;
    private String externalId;
}
