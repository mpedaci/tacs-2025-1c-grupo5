package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offer.OfferStatusInputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Offer.Status;
import utn.tacs.grupo5.entity.post.OfferedCard;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IOfferService;
import utn.tacs.grupo5.service.IPostService;
import utn.tacs.grupo5.service.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService implements IOfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final ICardService cardService;
    private final IPostService postService;
    private final IUserService userService;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, ICardService cardService,
            IPostService postService, IUserService userService) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.cardService = cardService;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Optional<Offer> get(UUID id) {
        Optional<Offer> offer = offerRepository.findById(id);
        offer.ifPresent(this::updateOfferInfo);
        return offer;
    }

    @Override
    public List<Offer> getAllByPostId(UUID postId) {
        if (postService.get(postId).isEmpty()) {
            throw new NotFoundException("Post not found");
        }

        List<Offer> offers = offerRepository.findAllByPostId(postId);
        offers.forEach(this::updateOfferInfo);
        return offers;
    }

    @Override
    public Offer save(OfferInputDto dto) {
        Offer offer = offerMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        offer.setPublishedAt(now);
        offer.setUpdatedAt(now);
        offer.setFinishedAt(null);
        offer.setStatus(Offer.Status.PENDING);
        return offerRepository.save(offer);
    }

    @Override
    public Offer update(UUID id, OfferInputDto dto) {
        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
        Offer offer = offerMapper.toEntity(dto);
        offer.setId(existingOffer.getId());
        LocalDateTime now = LocalDateTime.now();
        offer.setPublishedAt(existingOffer.getPublishedAt());
        offer.setStatus(existingOffer.getStatus());
        offer.setUpdatedAt(now);
        offer.setFinishedAt(null);
        return offerRepository.save(offer);
    }

    @Override
    public void delete(UUID offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public void updateStatus(UUID offerId, OfferStatusInputDto dto) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new NotFoundException("Offer not found"));

        LocalDateTime now = LocalDateTime.now();
        offer.setStatus(dto.getStatus());
        offer.setUpdatedAt(now);
        offer.setFinishedAt(now);

        if (Status.ACCEPTED.equals(dto.getStatus())) {
            postService.updateStatus(offer.getPostId(), Post.Status.FINISHED);
        }

        offerRepository.save(offer);
    }

    private void updateOfferInfo(Offer o) {
        o.setOfferer(userService.get(o.getOffererId()).orElseThrow(() -> {
            delete(o.getId());
            return new NotFoundException("Offerer not found");
        }));

        o.setPost(postService.get(o.getPostId()).orElseThrow(() -> {
            delete(o.getId());
            return new NotFoundException("Offer Post not found");
        }));

        List<OfferedCard> deletedCards = new ArrayList<>();
        o.getOfferedCards().forEach(
                oc -> cardService.get(oc.getCardId()).ifPresentOrElse(oc::setCard, () -> deletedCards.add(oc)));
        if (!deletedCards.isEmpty()) {
            o.getOfferedCards().removeAll(deletedCards);
            offerRepository.save(o);
        }
    }
}
