package utn.tacs.grupo5.repository.impl;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Offer.Status;
import utn.tacs.grupo5.repository.OfferRepository;

import java.util.*;

@Repository
public class InMemoryOfferRepository implements OfferRepository {

    private final List<Offer> offers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Offer save(Offer offer) {

        if (offer.getId() == null) {
            offer.setId(UUID.randomUUID());
        } else {
            deleteById(offer.getId()); // replace if exists
        }
        offers.add(offer);
        return offer;
    }

    @Override
    public Optional<Offer> findById(UUID id) {
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
    public void deleteById(UUID id) {
        synchronized (offers) {
            offers.removeIf(offer -> offer.getId().equals(id));
        }

    }

    @Override
    public List<Offer> findAllByPostId(UUID postId) {
        synchronized (offers) {
            return new ArrayList<Offer>(
                    offers.stream()
                            .filter(offer -> Objects.equals(offer.getPost().getId(), postId))
                            .toList());
        }
    }

    @Override
    public Long getCount() {
        synchronized (offers) {
            return Long.valueOf(offers.size());
        }
    }

    @Override
    public Long getCountByStatus(Status status) {
        synchronized (offers) {
            return offers.stream()
                    .filter(offer -> status != null && status.equals(offer.getStatus()))
                    .count();
        }
    }
}
