package utn.tacs.grupo5.telegrambot.dto.offer;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;

@Data
@NoArgsConstructor
public class OfferedCardOutputDto {

    private String image;
    private CardOutputDto card;
    private ConservationStatus conservationStatus;

}
