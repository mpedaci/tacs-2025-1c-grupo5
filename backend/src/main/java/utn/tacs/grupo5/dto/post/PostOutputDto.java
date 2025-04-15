package utn.tacs.grupo5.dto.post;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;

@Data
@ToString
@Schema(name = "Post Output Schema", description = "Post schema for output")
public class PostOutputDto {

    private Long id;
    private UserOutputDto user;
    private List<String> images;
    private CardOutputDto card;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<CardOutputDto> wantedCards;
    private String description;
    private Post.Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;

}
