package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.Offer;

import java.util.List;
import java.util.Optional;

public class InMemoryOfferRepository implements OfferRepository {
    @Override
    public Offer findByPublicationId(Long publicationId) {
        return null;
    }

    @Override
    public void deleteByPublicationId(Long publicationId) {

    }

    @Override
    public void deleteByUserId(Long userId) {

    }

    @Override
    public Offer save(Offer user) {
        return null;
    }

    @Override
    public Optional<Offer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Offer> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
