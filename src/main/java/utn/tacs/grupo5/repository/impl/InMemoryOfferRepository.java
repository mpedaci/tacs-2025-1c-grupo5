package utn.tacs.grupo5.repository.impl;

import org.springframework.stereotype.Repository;
import utn.tacs.grupo5.entity.Offer;
import utn.tacs.grupo5.repository.OfferRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOfferRepository implements OfferRepository {

    private final List<Offer> offers = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Optional<Offer> findByPublicationId(Long publicationId) {

        synchronized (offers) {
            return offers.stream().filter(offer -> offer.getPublication().getId().equals(publicationId)).findFirst();
        }
    }

    @Override
    public void deleteByPublicationId(Long publicationId) {
        synchronized (offers) {
            offers.removeIf(offer -> offer.getPublication().getId().equals(publicationId));
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        synchronized (offers) {
            offers.removeIf(offer -> offer.getOfferUser().getId().equals(userId));
        }
    }

    @Override
    public Offer save(Offer offer) {

        if (offer.getId() == null) {
            offer.setId(idGenerator.incrementAndGet());
        } else {
            deleteById(offer.getId()); // replace if exists
        }
        offers.add(offer);
        return offer;
    }

    @Override
    public Optional<Offer> findById(Long id) {
        synchronized (offers) {
            return offers.stream().filter(offer -> offer.getId().equals(id)).findFirst();
        }
    }

    @Override
    public List<Offer> findAll() {
        synchronized (offers) {
            return new ArrayList<>(offers);
        }
    }

    @Override
    public void deleteById(Long id) {
        synchronized (offers) {
            offers.removeIf(user -> user.getId().equals(id));
        }

    }

    @Override
    public List<Offer> findAllByPublicationId(Long publicationId) {
        synchronized (offers) {
            return new ArrayList<Offer>(
                    offers.stream()
                            .filter(offer -> Objects.equals(offer.getPublication().getId(), publicationId))
                            .toList());
        }
    }
}
