package utn.tacs.grupo5.telegrambot.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferedCardInputDto {

    private UUID cardId;
    private ConservationStatus conservationStatus;
    private String image;

}
