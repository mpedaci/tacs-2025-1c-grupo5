package utn.tacs.grupo5.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * This dto represents the post data received to create a new post.
 */
@Data
@NoArgsConstructor @EqualsAndHashCode @ToString
@Schema(name = "Post Input Schema", description = "Post schema for input")
public class PostInputDto {
    private Long userId;
    private List<String> images;

    //TODO: agregar la carta cuando este la entidad
    private String conservationStatus;
    private Double estimatedValue;
    //TODO: agregar lista de cartas deseadas cuando este la entidad

    private String postStatus;
}
