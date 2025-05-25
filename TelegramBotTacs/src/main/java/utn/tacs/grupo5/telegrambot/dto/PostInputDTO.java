package utn.tacs.grupo5.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInputDTO {
    private String id;
    private UserInputDTO user;
    private List<String> images;
    private CardInputDTO card;
    private String conservationStatus;
    private int estimatedValue;
    private List<CardInputDTO> wantedCards;
    private String description;
    private String status;
    private String publishedAt;
    private String updatedAt;
    private String finishedAt;
}
