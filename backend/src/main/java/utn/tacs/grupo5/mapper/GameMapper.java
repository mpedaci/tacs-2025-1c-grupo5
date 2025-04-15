package utn.tacs.grupo5.mapper;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.dto.game.GameOutputDto;
import utn.tacs.grupo5.entity.card.Game;

@Component
public class GameMapper implements IMapper<Game, GameInputDto, GameOutputDto> {

    @Override
    public GameOutputDto toDto(Game game) {
        GameOutputDto dto = new GameOutputDto();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setDescription(game.getDescription());
        return dto;
    }

    @Override
    public Game toEntity(GameInputDto dto) {
        Game game = new Game();
        game.setName(dto.getName());
        game.setDescription(dto.getDescription());
        return game;
    }

}
