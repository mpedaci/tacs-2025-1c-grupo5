package utn.tacs.grupo5.repository.impl;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.repository.OfferRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOfferRepository implements OfferRepository {

    private final List<Offer> offers = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

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
            offers.removeIf(offer -> offer.getId().equals(id));
        }

    }

    @Override
    public List<Offer> findAllByPostId(Long postId) {
        synchronized (offers) {
            return new ArrayList<Offer>(
                    offers.stream()
                            .filter(offer -> Objects.equals(offer.getPost().getId(), postId))
                            .toList());
        }
    }
}
