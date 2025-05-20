package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.GameMapper;
import utn.tacs.grupo5.repository.impl.MongoGameRepository;
import utn.tacs.grupo5.service.IGameService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService implements IGameService {
    private final MongoGameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(MongoGameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        insertDefaultGames(gameRepository);
    }

    private void insertDefaultGames(MongoGameRepository gameRepository) {
        gameRepository.findByTitle("Magic the Gathering").ifPresentOrElse(game -> {}, () -> {
            Game magic = new Game();
            magic.setId(UUID.randomUUID());
            magic.setTitle("Magic the Gathering");
            magic.setName(Game.Name.MAGIC);
            magic.setDescription(
                    "Magic: The Gathering is a collectible card game created by mathematician Richard Garfield and " +
                            "published by Wizards of the Coast.");
            gameRepository.save(magic);
        });

        gameRepository.findByTitle("Pokemon").ifPresentOrElse(game -> {}, () -> {
            Game pokemon = new Game();
            pokemon.setId(UUID.randomUUID());
            pokemon.setTitle("Pokemon");
            pokemon.setName(Game.Name.POKEMON);
            pokemon.setDescription(
                    "Pokémon is a media franchise created by Satoshi Tajiri and Ken Sugimori, and managed by The " +
                            "Pokémon Company.");
            gameRepository.save(pokemon);
        });

        gameRepository.findByTitle("Yu-Gi-Oh!").ifPresentOrElse(game -> {}, () -> {
            Game yugioh = new Game();
            yugioh.setId(UUID.randomUUID());
            yugioh.setTitle("Yu-Gi-Oh!");
            yugioh.setName(Game.Name.YUGIOH);
            yugioh.setDescription("Yu-Gi-Oh! is a Japanese manga series about gaming, created by Kazuki Takahashi.");
            gameRepository.save(yugioh);
        });
    }

    @Override
    public Optional<Game> get(UUID id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game save(GameInputDto dto) {
        Game game = gameMapper.toEntity(dto);
        return gameRepository.save(game);
    }

    @Override
    public Game update(UUID id, GameInputDto dto) {
        gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game not found"));
        Game game = gameMapper.toEntity(dto);
        game.setId(id);
        return gameRepository.save(game);
    }

    @Override
    public void delete(UUID id) {
        gameRepository.deleteById(id);
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }
}
