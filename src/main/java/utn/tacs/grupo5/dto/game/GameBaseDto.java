package utn.tacs.grupo5.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class GameBaseDto {

    private String name;
    private String description;

}
