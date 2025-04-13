package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.Offer;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.IOfferService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<Offer> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Offer save(OfferInputDto offerInputDto) {

        offerRepository.findById(offerInputDto.getId())
                .ifPresent(offer -> {
                    throw new ConflictException("Offer already exists");
                });

        OfferMapper offerMapper = new OfferMapper();

        Offer offer = offerMapper.toEntity(offerInputDto);

        offer.setId(offerInputDto.getId());
        offer.setOfferUser(offerInputDto.getOfferUser());
        offer.setPublication(offerInputDto.getPublication());
        offer.setOfferedCards(offerInputDto.getOfferedCards());
        offer.setMoney(offerInputDto.getMoney());
        offer.setState(offerInputDto.getState());
        offer.setPublicationDate(LocalDateTime.now());
        offer.setOfferEndDate(LocalDateTime.now());

        return offerRepository.save(offer);
    }

    //consultar funcionamiento de update para las ofertas y si lo recibe por parametro
    @Override
    public Offer update(Long id, OfferInputDto offerInputDto) {

        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));

        OfferMapper offerMapper = new OfferMapper();
        Offer offerUpdate = offerMapper.toEntity(offerInputDto);
        offerUpdate.setState(offerInputDto.getState());
        return offerRepository.save(offerUpdate);
    }

    @Override
    public void delete(Long aLong) {

    }
}
