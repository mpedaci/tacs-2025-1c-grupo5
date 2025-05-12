package utn.tacs.grupo5.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import utn.tacs.grupo5.dto.post.PostInputDto;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Chatdata implements Serializable {
    UUID user;
    String game;
    PostInputDto postInputDto;
    String helpStringValue;
}
