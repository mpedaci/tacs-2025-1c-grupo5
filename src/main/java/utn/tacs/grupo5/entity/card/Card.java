package utn.tacs.grupo5.entity.card;

import lombok.Data;

@Data
public class Card {

    private Long id;
    private Game game;
    private String name;
    private String description;
    // private String imageUrl;
    // private String externalId;

}
