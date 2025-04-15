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
