package utn.tacs.grupo5.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.post.Offer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@Schema(name = "Offer", description = "Offer schema for output")
public class OfferOutputDto {

    private Long id;
    private BigDecimal money;
    private List<OfferedCardOutputDto> offeredCards;
    private UserOutputDto offerer;
    private Long postId;
    private Offer.Status state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;

}
