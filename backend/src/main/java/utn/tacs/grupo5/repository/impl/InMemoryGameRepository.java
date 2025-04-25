package utn.tacs.grupo5.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.repository.GameRepository;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private final List<Game> games = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    public InMemoryGameRepository() {
        Game magic = new Game();
        magic.setId(0L);
        magic.setTitle("Magic the Gathering");
        magic.setName(Game.Name.MAGIC);
        magic.setDescription(
                "Magic: The Gathering is a collectible card game created by mathematician Richard Garfield and published by Wizards of the Coast.");
        games.add(magic);

        Game pokemon = new Game();
        pokemon.setId(1L);
        pokemon.setTitle("Pokemon");
        pokemon.setName(Game.Name.POKEMON);
        pokemon.setDescription(
                "Pokémon is a media franchise created by Satoshi Tajiri and Ken Sugimori, and managed by The Pokémon Company.");
        games.add(pokemon);

        Game yugioh = new Game();
        yugioh.setId(2L);
        yugioh.setTitle("Yu-Gi-Oh!");
        yugioh.setName(Game.Name.YUGIOH);
        yugioh.setDescription("Yu-Gi-Oh! is a Japanese manga series about gaming, created by Kazuki Takahashi.");
        games.add(yugioh);
    }

    @Override
    public Game save(Game games) {
        if (games.getId() == null) {
            games.setId(idGenerator.incrementAndGet());
        } else {
            deleteById(games.getId()); // replace if exists
        }
        this.games.add(games);
        return games;
    }

    @Override
    public Optional<Game> findById(Long id) {
        synchronized (games) {
            return games.stream()
                    .filter(game -> game.getId().equals(id))
                    .findFirst();
        }
    }

    @Override
    public List<Game> findAll() {
        synchronized (games) {
            return new ArrayList<>(games);
        }
    }

    @Override
    public void deleteById(Long id) {
        synchronized (games) {
            games.removeIf(game -> game.getId().equals(id));
        }
    }

}
