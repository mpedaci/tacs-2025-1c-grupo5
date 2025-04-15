package utn.tacs.grupo5.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.entity.post.ConservationStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * This dto represents the post data received to create a new post.
 */
@Data
@NoArgsConstructor
@Schema(name = "Post Input", description = "Post schema for input")
public class PostInputDto {

    private Long userId;
    private List<String> images;
    private Long cardId;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<Long> wantedCardsIds;
    private String description;

}
