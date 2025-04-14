package utn.tacs.grupo5.dto.offer;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(name = "Offer Output Schema", description = "Offer schema for output")
public class OfferOutputDto {

//    private Long id;
//
//    @Schema(description = "Creation date of the publication", example = "YYYY-MM-DD")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime publicationDate;
//
//    @Schema(description = "End date of the offer", example = "YYYY-MM-DD")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime offerEndDate;
//
//    public OfferOutputDto(OfferBaseDto offerBaseDto,
//                          LocalDateTime publicationDate,
//                          LocalDateTime offerEndDate) {
//        super(offerBaseDto.getId(),
//                offerBaseDto.getOfferUser(),
//                offerBaseDto.getPublication(),
//                offerBaseDto.getOfferedCards(),
//                offerBaseDto.getMoney(),
//                offerBaseDto.getState(),
//                offerBaseDto.getPublicationDate(),
//                offerBaseDto.getFinished()
//        );
//               this.publicationDate = publicationDate;
//        this.offerEndDate = offerEndDate;
//    }
}
