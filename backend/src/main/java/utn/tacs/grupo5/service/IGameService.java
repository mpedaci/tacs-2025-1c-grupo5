package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.entity.post.Post;

import java.util.List;
import java.util.UUID;

public interface IGameService extends ICRUDService<Game, UUID, GameInputDto> {
    List<Game> getAll();
}
