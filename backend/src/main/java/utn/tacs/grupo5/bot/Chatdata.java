package utn.tacs.grupo5.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.User;

import java.util.UUID;

@AllArgsConstructor
@Data
public class Chatdata {
    UUID user;
    String game;
    PostInputDto postInputDto;
}
