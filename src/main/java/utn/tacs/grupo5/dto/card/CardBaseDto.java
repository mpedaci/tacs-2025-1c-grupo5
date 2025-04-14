package utn.tacs.grupo5.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Card Schema", description = "Card schema")
public class CardBaseDto {

    private String name;
    private String description;
    // private String imageUrl;

}
