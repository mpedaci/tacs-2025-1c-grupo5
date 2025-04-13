package utn.tacs.grupo5.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor @EqualsAndHashCode @ToString
@Schema(name = "Post Output Schema", description = "Post schema for output")
public class PostOutputDto {
    private Long id;
}
