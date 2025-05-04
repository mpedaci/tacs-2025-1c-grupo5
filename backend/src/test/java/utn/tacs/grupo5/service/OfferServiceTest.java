package utn.tacs.grupo5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.impl.OfferService;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    OfferRepository offerRepository;

    @Mock
    OfferMapper offerMapper;

    @Mock
    IPostService postService;

    @InjectMocks
    OfferService offerService;

    @Test
    void get_shouldReturnOptionalOffer_whenOfferExists() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setId(offerId);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        Optional<Offer> result = offerService.get(offerId);

        assertEquals(offerId, result.get().getId());
    }

    @Test
    void get_shouldReturnEmptyOptional_whenOfferDoesNotExist() {
        Long offerId = 1L;

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
        Long offerId = 1L;
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
        Long offerId = 1L;
        OfferInputDto dto = new OfferInputDto();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.update(offerId, dto));
    }

    @Test
    void delete_shouldDeleteOffer_whenOfferExists() {
        Long offerId = 1L;

        offerService.delete(offerId);

        verify(offerRepository).deleteById(offerId);
    }

    @Test
    void getAllByPostId_shouldReturnListOfOffers_whenPostExists() {
        Long postId = 1L;
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        List<Offer> offers = List.of(offer1, offer2);

        when(postService.get(postId)).thenReturn(Optional.of(new Post()));
        when(offerRepository.findAllByPostId(postId)).thenReturn(offers);

        List<Offer> result = offerService.getAllByPostId(postId);

        assertEquals(2, result.size());
    }

    @Test
    void getAllByPostId_shouldThrowNotFoundException_whenPostDoesNotExist() {
        Long postId = 1L;

        when(postService.get(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.getAllByPostId(postId));
    }

    @Test
    void updateStatus_shouldUpdateOfferStatus_whenOfferExists() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setPost(new Post());
        Offer.Status newStatus = Offer.Status.ACCEPTED;

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        offerService.updateStatus(offerId, newStatus);

        assertEquals(newStatus, offer.getStatus());
        verify(postService).updateStatus(offer.getPost().getId(), Post.Status.FINISHED);
        verify(offerRepository).save(offer);
    }

    @Test
    void updateStatus_shouldThrowNotFoundException_whenOfferDoesNotExist() {
        Long offerId = 1L;
        Offer.Status newStatus = Offer.Status.ACCEPTED;

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.updateStatus(offerId, newStatus));
    }
}
