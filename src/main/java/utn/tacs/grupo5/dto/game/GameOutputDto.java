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
@Schema(name = "Game", description = "Game schema for output")
public class GameOutputDto extends GameBaseDto {

    private Long id;

    public GameOutputDto(GameBaseDto dto, Long id) {
        super(
                dto.getName(),
                dto.getDescription());
        this.id = id;
    }

}
