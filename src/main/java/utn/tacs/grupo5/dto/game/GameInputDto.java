package utn.tacs.grupo5.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Game Input", description = "Game schema for input")
public class GameInputDto extends GameBaseDto {

    // private String apiUrl;

    public GameInputDto(GameBaseDto dto) {
        super(
                dto.getName(),
                dto.getDescription());
    }

}
