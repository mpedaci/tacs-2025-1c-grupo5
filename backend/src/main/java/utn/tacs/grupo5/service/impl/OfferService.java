package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.Offer.Status;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.IOfferService;
import utn.tacs.grupo5.service.IPostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final IPostService postService;

    public OfferService(OfferRepository offerRepository,
            OfferMapper offerMapper,
            IPostService postService) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.postService = postService;
    }

    @Override
    public Optional<Offer> get(Long aLong) {
        return offerRepository.findById(aLong);
    }

    @Override
    public List<Offer> getAllByPostId(Long postId) {
        if (postService.get(postId).isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        return offerRepository.findAllByPostId(postId);
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
    public Offer update(Long id, OfferInputDto dto) {
        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
        Offer offer = offerMapper.toEntity(dto);
        offer.setUpdatedAt(LocalDateTime.now());
        offer.setId(existingOffer.getId());
        return offerRepository.save(offer);
    }

    @Override
    public void delete(Long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public void updateStatus(Long offerId, Status newStatus) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new NotFoundException("Offer not found"));

        LocalDateTime now = LocalDateTime.now();
        offer.setStatus(newStatus);
        offer.setUpdatedAt(now);
        offer.setFinishedAt(now);

        if (newStatus == Status.ACCEPTED) {
            postService.updateStatus(offer.getPost().getId(), Post.Status.FINISHED);
        }

        offerRepository.save(offer);
    }

}
