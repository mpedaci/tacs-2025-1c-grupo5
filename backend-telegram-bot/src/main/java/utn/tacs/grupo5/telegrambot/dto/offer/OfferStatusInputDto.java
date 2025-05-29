package utn.tacs.grupo5.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.entity.post.Offer;

@Data
@NoArgsConstructor
@Schema(name = "Offer Status Input", description = "Offer status schema for input")
public class OfferStatusInputDto {
    private Offer.Status status;
}
