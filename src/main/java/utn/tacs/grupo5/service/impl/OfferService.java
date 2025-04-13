package utn.tacs.grupo5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferState;
import utn.tacs.grupo5.entity.offer.OfferedCard;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferedCardRepository;
import utn.tacs.grupo5.repository.impl.InMemoryPostRepository;
import utn.tacs.grupo5.repository.impl.InMemoryUserRepository;
import utn.tacs.grupo5.service.IOfferService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private InMemoryPostRepository inMemoryPostRepository;
    @Autowired
    private InMemoryOfferRepository inMemoryOfferRepository;
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;
    @Autowired
    private InMemoryOfferedCardRepository inmemoryOfferedCardRepository;

    @Override
    public Optional<Offer> get(Long aLong) {
        return inMemoryOfferRepository.findById(aLong);
    }

    @Override
    public Offer getById(Long postId, Long offerId) {

        inMemoryPostRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));

        Offer offer = get(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));

//        TODO: No se si el mensaje de error esta bien
        if (!offer.getPost().getId().equals(postId)) {
            throw new NotFoundException("Post does not belong to this offer");
        }

        return offer;
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> getAllByPublicationId(Long publicationId) {
        if (inMemoryPostRepository.findById(publicationId).isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        return offerRepository.findAllByPublicationId(publicationId);
    }


    @Override
    public Offer save(OfferInputDto offerInputDto) {

        if (inMemoryPostRepository.findById(offerInputDto.getPostId()).isEmpty()) {
            throw new NotFoundException("Post not found");
        }


        Offer offer = new Offer();
        User user = inMemoryUserRepository.findById(offerInputDto.getOffererId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        offer.setUser(user);

        Post post = inMemoryPostRepository.findById(offerInputDto.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));
        offer.setPost(post);

        List<OfferedCard> offeredCards = new ArrayList<>();
//        TODO: se podria extraer esta creacion de cartas en otro lado
        offerInputDto.getOfferedCards().forEach(offeredCard -> {
            OfferedCard card = new OfferedCard();
            card.setImageUrl(offeredCard.getImage());
//            TODO: validar que sea correcto
            card.setConservationStatus(ConservationStatus.valueOf(offeredCard.getConservationStatus()));
            inmemoryOfferedCardRepository.save(card);
            offeredCards.add(card);
        });
        offer.setOfferedCards(offeredCards);

        offer.setMoney(offerInputDto.getMoney());

        offer.setState(OfferState.PENDING);

        offer.setPublicationDate(LocalDateTime.now());

        return offerRepository.save(offer);
    }

    //consultar funcionamiento de update para las ofertas y si lo recibe por parametro
    @Override
    public Offer update(Long id, OfferInputDto offerInputDto) {
//
//        Offer existingOffer = offerRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Offer not found"));
//
//        OfferMapper offerMapper = new OfferMapper();
//        Offer offerUpdate = offerMapper.toEntity(offerInputDto);
//        offerUpdate.setState(offerInputDto.getState());
//        return offerRepository.save(offerUpdate);
        return new Offer();
    }

    @Override
    public void delete(Long aLong) {

    }
}
