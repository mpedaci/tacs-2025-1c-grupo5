package utn.tacs.grupo5.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "Game", description = "Game schema for output")
public class GameOutputDto {

    private UUID id;
    private String title;
    private String description;

}
