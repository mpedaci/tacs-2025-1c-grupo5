package utn.tacs.grupo5.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.entity.post.ConservationStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * This dto represents the post data received to create a new post.
 */
@Data
@NoArgsConstructor
@Schema(name = "Post Input", description = "Post schema for input")
public class PostInputDto {

    private UUID userId;
    private List<String> images;
    private UUID cardId;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<UUID> wantedCardsIds;
    private String description;

}
