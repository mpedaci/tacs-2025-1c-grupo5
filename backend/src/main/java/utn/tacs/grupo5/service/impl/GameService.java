package utn.tacs.grupo5.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.GameMapper;
import utn.tacs.grupo5.repository.GameRepository;
import utn.tacs.grupo5.service.IGameService;

@Service
public class GameService implements IGameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public Optional<Game> get(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game save(GameInputDto dto) {
        Game game = gameMapper.toEntity(dto);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Long id, GameInputDto dto) {
        gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game not found"));
        Game game = gameMapper.toEntity(dto);
        game.setId(id);
        return gameRepository.save(game);
    }

    @Override
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

}
