package utn.tacs.grupo5.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Game Input", description = "Game schema for input")
public class GameInputDto {

    // private String apiUrl;
    private String title;
    private String name;
    private String description;

}
