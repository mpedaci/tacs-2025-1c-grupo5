package utn.tacs.grupo5.dto.offeredCard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(name = "Offered card Input Schema", description = "Offered card schema for input")
public class OfferedCardInputDto {
    private Long cardId;
    private String image;
    private String conservationStatus;
}
