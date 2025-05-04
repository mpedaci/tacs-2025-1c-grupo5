package utn.tacs.grupo5.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;

@Data
@NoArgsConstructor
@Schema(name = "Offered card", description = "Offered card")
public class OfferedCardOutputDto {

    private String image;
    private CardOutputDto card;
    private ConservationStatus conservationStatus;

}
