package utn.tacs.grupo5.entity.post;

import lombok.Data;
import utn.tacs.grupo5.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {
    private Long id;
    private User user;
    private List<String> images;

    //TODO: agregar la carta cuando este la entidad
    private ConservationStatus conservationStatus;
    private Double estimatedValue;
    //TODO: agregar lista de cartas deseadas cuando este la entidad

    private PostStatus postStatus;
    private LocalDateTime publishDate;
    private LocalDateTime finishDate;
}
