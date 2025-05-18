package utn.tacs.grupo5.entity.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "cards")
@Data
public class Card {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonIgnore
    private UUID gameId;
    @Transient
    private Game game;
    private String name;
    private String imageUrl;
    private String externalId;
}
