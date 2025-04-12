package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.Offer;
import utn.tacs.grupo5.service.IOfferService;

import java.util.Optional;

@Service
public class OfferService implements IOfferService {
    @Override
    public Optional<Offer> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Offer save(OfferInputDto offerInputDto) {
        return null;
    }

    @Override
    public Offer update(Long aLong, OfferInputDto offerInputDto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
