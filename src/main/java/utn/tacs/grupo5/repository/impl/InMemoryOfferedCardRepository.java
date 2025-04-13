package utn.tacs.grupo5.repository.impl;

import org.springframework.stereotype.Repository;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferedCard;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.OfferedCardRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOfferedCardRepository implements OfferedCardRepository {

    private final List<OfferedCard> cards = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public OfferedCard save(OfferedCard card) {

        if (card.getId() == null) {
            card.setId(idGenerator.incrementAndGet());
        } else {
            deleteById(card.getId()); // replace if exists
        }
        cards.add(card);
        return card;
    }

    @Override
    public Optional<OfferedCard> findById(Long id) {
        synchronized (cards) {
            return cards.stream().filter(card -> card.getId().equals(id)).findFirst();
        }
    }

    @Override
    public List<OfferedCard> findAll() {
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


}
