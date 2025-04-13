package utn.tacs.grupo5.dto.offer;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utn.tacs.grupo5.entity.Publication;
import utn.tacs.grupo5.entity.OfferedCard;
import utn.tacs.grupo5.entity.OfferState;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(name = "Offer Output Schema", description = "Offer schema for output")
public class OfferOutputDto extends OfferBaseDto {


    @Schema(description = "Creation date of the publication", example = "YYYY-MM-DD")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publicationDate;

    @Schema(description = "End date of the offer", example = "YYYY-MM-DD")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offerEndDate;

    public OfferOutputDto(OfferBaseDto offerBaseDto,
                          LocalDateTime publicationDate,
                          LocalDateTime offerEndDate) {
        super(offerBaseDto.getId(),
                offerBaseDto.getOfferUser(),
                offerBaseDto.getPublication(),
                offerBaseDto.getOfferedCards(),
                offerBaseDto.getMoney(),
                offerBaseDto.getState(),
                offerBaseDto.getPublicationDate(),
                offerBaseDto.getFinished()
        );
               this.publicationDate = publicationDate;
        this.offerEndDate = offerEndDate;
    }
}
