package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offer.OfferStatusInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.impl.MongoOfferRepository;
import utn.tacs.grupo5.service.impl.OfferService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    MongoOfferRepository offerRepository;

    @Mock
    OfferMapper offerMapper;

    @Mock
    IPostService postService;

    @Mock
    IUserService userService;

    @InjectMocks
    OfferService offerService;

    @Test
    void get_shouldReturnOptionalOffer_whenOfferExists() {
        UUID offerId = UUID.randomUUID();
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setOffererId(UUID.randomUUID());
        offer.setPostId(UUID.randomUUID());
        offer.setOfferedCards(new ArrayList<>());

        when(offerRepository.findById(any(UUID.class))).thenReturn(Optional.of(offer));
        when(userService.get(any(UUID.class))).thenReturn(Optional.of(new User()));
        when(postService.get(any(UUID.class))).thenReturn(Optional.of(new Post()));

        Optional<Offer> result = offerService.get(offerId);

        assertTrue(result.isPresent());
        assertEquals(offerId, result.get().getId());
    }

    @Test
    void get_shouldReturnEmptyOptional_whenOfferDoesNotExist() {
        UUID offerId = UUID.randomUUID();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        Optional<Offer> result = offerService.get(offerId);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void save_shouldSaveOffer_whenValidInput() {
        OfferInputDto dto = new OfferInputDto();
        Offer offer = new Offer();

        when(offerMapper.toEntity(dto)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);

        Offer result = offerService.save(dto);

        assertEquals(offer, result);
    }

    @Test
    void update_shouldUpdateOffer_whenOfferExists() {
        UUID offerId = UUID.randomUUID();
        OfferInputDto dto = new OfferInputDto();
        Offer existingOffer = new Offer();
        existingOffer.setId(offerId);
        Offer updatedOffer = new Offer();
        updatedOffer.setId(offerId);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerMapper.toEntity(dto)).thenReturn(updatedOffer);
        when(offerRepository.save(updatedOffer)).thenReturn(updatedOffer);

        Offer result = offerService.update(offerId, dto);

        assertEquals(offerId, result.getId());
    }

    @Test
    void update_shouldThrowNotFoundException_whenOfferDoesNotExist() {
        UUID offerId = UUID.randomUUID();
        OfferInputDto dto = new OfferInputDto();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.update(offerId, dto));
    }

    @Test
    void delete_shouldDeleteOffer_whenOfferExists() {
        UUID offerId = UUID.randomUUID();

        offerService.delete(offerId);

        verify(offerRepository).deleteById(offerId);
    }

    @Test
    void getAllByPostId_shouldReturnListOfOffers_whenPostExists() {
        UUID postId = UUID.randomUUID();
        Offer offer1 = new Offer();
        offer1.setOffererId(UUID.randomUUID());
        offer1.setPostId(UUID.randomUUID());
        offer1.setOfferedCards(new ArrayList<>());
        Offer offer2 = new Offer();
        offer2.setOffererId(UUID.randomUUID());
        offer2.setPostId(UUID.randomUUID());
        offer2.setOfferedCards(new ArrayList<>());
        List<Offer> offers = List.of(offer1, offer2);

        when(postService.get(any(UUID.class))).thenReturn(Optional.of(new Post()));
        when(userService.get(any(UUID.class))).thenReturn(Optional.of(new User()));
        when(offerRepository.findAllByPostId(postId)).thenReturn(offers);

        List<Offer> result = offerService.getAllByPostId(postId);

        assertEquals(2, result.size());
    }

    @Test
    void getAllByPostId_shouldThrowNotFoundException_whenPostDoesNotExist() {
        UUID postId = UUID.randomUUID();

        when(postService.get(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.getAllByPostId(postId));
    }

    @Test
    void updateStatus_shouldUpdateOfferStatus_whenOfferExists() {
        UUID offerId = UUID.randomUUID();
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setPost(new Post());

        OfferStatusInputDto dto = new OfferStatusInputDto();
        dto.setStatus(Offer.Status.ACCEPTED);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        offerService.updateStatus(offerId, dto);

        assertEquals(dto.getStatus(), offer.getStatus());
        verify(postService).updateStatus(offer.getPostId(), Post.Status.FINISHED);
        verify(offerRepository).save(offer);
    }

    @Test
    void updateStatus_shouldThrowNotFoundException_whenOfferDoesNotExist() {
        UUID offerId = UUID.randomUUID();
        OfferStatusInputDto dto = new OfferStatusInputDto();
        dto.setStatus(Offer.Status.ACCEPTED);

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.updateStatus(offerId, dto));
    }
}
