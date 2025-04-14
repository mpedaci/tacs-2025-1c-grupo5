package utn.tacs.grupo5.repository.impl;

import org.springframework.stereotype.Repository;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.OfferedCardRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOfferRepository implements OfferRepository {

    private final List<Offer> offers = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    private OfferedCardRepository offeredCardRepository;

    public InMemoryOfferRepository(OfferedCardRepository offeredCardRepository) {
        this.offeredCardRepository = offeredCardRepository;
    }

    @Override
    public Optional<Offer> findByPostId(Long postId) {

        synchronized (offers) {
            return offers.stream().filter(offer -> offer.getPost().getId().equals(postId)).findFirst();
        }
    }

    @Override
    public void deleteByPostId(Long postId) {
        synchronized (offers) {
            offers.removeIf(offer -> offer.getPost().getId().equals(postId));
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        synchronized (offers) {
            offers.removeIf(offer -> offer.getUser().getId().equals(userId));
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
            Offer offerToDelete = findById(id).orElseThrow(() -> new IllegalArgumentException("Offer not found"));

            offerToDelete.getOfferedCards().forEach(offeredCardRepository::deleteByCard);

            offers.remove(offerToDelete);
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
