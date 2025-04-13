package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.Offer;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryPostRepository;
import utn.tacs.grupo5.service.IOfferService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final InMemoryPostRepository inMemoryPostRepository;
    private final InMemoryOfferRepository inMemoryOfferRepository;

    public OfferService(OfferRepository offerRepository, InMemoryPostRepository inMemoryPostRepository,
                        InMemoryOfferRepository inMemoryOfferRepository) {
        this.offerRepository = offerRepository;
        this.inMemoryPostRepository = inMemoryPostRepository;
        this.inMemoryOfferRepository = inMemoryOfferRepository;
    }

    @Override
    public Optional<Offer> get(Long aLong) {
        return inMemoryOfferRepository.findById(aLong);
    }

    @Override
    public Offer getById(Long postId, Long offerId) {

        inMemoryPostRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));

        Offer offer = get(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));

//        TODO: No se si el mensaje de error esta bien
        if(!offer.getPublication().getId().equals(postId)) {
            throw new NotFoundException("Post does not belong to this offer");
        }

        return offer;
    }

    @Override
    public List<Offer> getAll() {return offerRepository.findAll();}

    @Override
    public List<Offer> getAllByPublicationId(Long publicationId) {
        if(inMemoryPostRepository.findById(publicationId).isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        return offerRepository.findAllByPublicationId(publicationId);
    }


    @Override
    public Offer save(OfferInputDto offerInputDto) {

        offerRepository.findById(offerInputDto.getId())
                .ifPresent(offer -> {
                    throw new ConflictException("Offer already exists");
                });

        if(inMemoryPostRepository.findById(offerInputDto.getPublication().getId()).isEmpty()) {
            throw new NotFoundException("Post not found");
        }

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
