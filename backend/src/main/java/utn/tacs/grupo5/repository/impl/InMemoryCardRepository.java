package utn.tacs.grupo5.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.repository.CardRepository;

@Repository
public class InMemoryCardRepository implements CardRepository {

    private final List<Card> cards = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Card save(Card card) {
        if (card.getId() == null) {
            card.setId(idGenerator.incrementAndGet());
        } else {
            deleteById(card.getId()); // replace if exists
        }
        cards.add(card);
        return card;
    }

    @Override
    public Optional<Card> findById(Long id) {
        synchronized (cards) {
            return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst();
        }
    }

    @Override
    public List<Card> findAll() {
        synchronized (cards) {
            return new ArrayList<>(cards);
        }
    }

    @Override
    public void deleteById(Long id) {
        synchronized (cards) {
            cards.removeIf(card -> card.getId().equals(id));
        }
    }

    @Override
    public List<Card> findByGameId(Long gameId) {
        synchronized (cards) {
            return cards.stream()
                    .filter(card -> card.getGame().getId().equals(gameId))
                    .toList();
        }
    }

}
