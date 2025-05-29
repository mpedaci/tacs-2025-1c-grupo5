package utn.tacs.grupo5.telegrambot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.dto.user.UserOutputDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class PostOutputDto {

    private UUID id;
    private UserOutputDto user;
    private List<String> images;
    private CardOutputDto card;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<CardOutputDto> wantedCards;
    private String description;
    private PostStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;

}
