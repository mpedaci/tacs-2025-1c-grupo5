package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardInputDTO {
    private String id;
    private GameInputDTO game;
    private String name;
    private String imageUrl;
    private String externalId;

}
