package utn.tacs.grupo5.entity.offer;

import lombok.Data;
import utn.tacs.grupo5.entity.post.ConservationStatus;

@Data
public class OfferedCard {
    private Long id;
//    TODO: agregar la carta cuando esté
    private String imageUrl;
    private ConservationStatus conservationStatus;
}
