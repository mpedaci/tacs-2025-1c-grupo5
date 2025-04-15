package utn.tacs.grupo5.dto.offer;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.entity.post.ConservationStatus;

@Data
@NoArgsConstructor
public class OfferedCardInputDto {

    // private String image;
    private Long cardId;
    private ConservationStatus conservationStatus;

}
